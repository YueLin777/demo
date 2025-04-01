require('dotenv').config();
const express = require('express');
const cors = require('cors');
const mysql = require('mysql2/promise');

const app = express();
app.use(cors());
app.use(express.json());

// 数据库连接配置
const dbConfig = {
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
  port: 3306  // 明确指定 MySQL 端口
};

// 创建数据库连接池
const pool = mysql.createPool(dbConfig);

// 初始化数据库表
async function initDatabase() {
  try {
    const connection = await pool.getConnection();
    await connection.execute(`
      CREATE TABLE IF NOT EXISTS webpage_info (
        id INT AUTO_INCREMENT PRIMARY KEY,
        title VARCHAR(255),
        url TEXT,
        description TEXT,
        content TEXT,
        timestamp DATETIME
      )
    `);
    connection.release();
    console.log('数据库表初始化成功');
  } catch (error) {
    console.error('数据库表初始化失败:', error);
  }
}

// 保存网页信息的接口
app.post('/api/save', async (req, res) => {
  try {
    const { title, url, description, text, timestamp } = req.body;
    const connection = await pool.getConnection();
    
    await connection.execute(
      'INSERT INTO webpage_info (title, url, description, content, timestamp) VALUES (?, ?, ?, ?, ?)',
      [title, url, description, text, new Date(timestamp)]
    );
    
    connection.release();
    res.json({ success: true, message: '数据保存成功' });
  } catch (error) {
    console.error('保存数据失败:', error);
    res.status(500).json({ success: false, message: '保存数据失败' });
  }
});

// 获取所有保存的网页信息
app.get('/api/pages', async (req, res) => {
  try {
    const connection = await pool.getConnection();
    const [rows] = await connection.execute('SELECT * FROM webpage_info ORDER BY timestamp DESC');
    connection.release();
    res.json({ success: true, data: rows });
  } catch (error) {
    console.error('获取数据失败:', error);
    res.status(500).json({ success: false, message: '获取数据失败' });
  }
});

// 获取数据库统计信息
app.get('/api/stats', async (req, res) => {
  try {
    const connection = await pool.getConnection();
    
    // 获取总记录数
    const [countResult] = await connection.execute('SELECT COUNT(*) as total FROM webpage_info');
    
    // 获取最新的5条记录
    const [latestRecords] = await connection.execute(
      'SELECT id, title, url, timestamp FROM webpage_info ORDER BY timestamp DESC LIMIT 5'
    );
    
    // 获取数据库大小信息
    const [sizeInfo] = await connection.execute(`
      SELECT 
        table_name AS 'Table',
        round(((data_length + index_length) / 1024 / 1024), 2) AS 'Size (MB)'
      FROM information_schema.TABLES
      WHERE table_schema = ? AND table_name = 'webpage_info'
    `, [process.env.DB_NAME]);
    
    connection.release();
    
    res.json({
      success: true,
      stats: {
        totalRecords: countResult[0].total,
        latestRecords,
        databaseInfo: sizeInfo[0]
      }
    });
  } catch (error) {
    console.error('获取统计信息失败:', error);
    res.status(500).json({ success: false, message: '获取统计信息失败' });
  }
});

// 初始化数据库并启动服务器
initDatabase().then(() => {
  const port = process.env.PORT || 3000;  // Web 服务器端口
  app.listen(port, '127.0.0.1', () => {  // 明确指定监听本地地址
    console.log(`服务器运行在 http://localhost:${port}`);
  });
}); 