// 保存页面信息的功能
document.getElementById('savePageInfo').addEventListener('click', async () => {
  const statusDiv = document.getElementById('status');
  statusDiv.textContent = '正在保存...';

  try {
    // 获取当前标签页
    const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });

    // 确保内容脚本已注入
    try {
      await chrome.scripting.executeScript({
        target: { tabId: tab.id },
        files: ['content.js']
      });
    } catch (e) {
      // 如果脚本已经注入，会抛出错误，我们可以忽略它
      console.log('Content script may already be injected');
    }

    // 向内容脚本发送消息，请求页面信息
    const response = await chrome.tabs.sendMessage(tab.id, { action: 'getPageInfo' });

    if (!response) {
      throw new Error('未能获取页面信息');
    }

    // 发送数据到服务器
    const serverResponse = await fetch('http://localhost:3000/api/save', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(response)
    });

    if (!serverResponse.ok) {
      throw new Error('服务器保存失败');
    }

    statusDiv.textContent = '保存成功！';
    setTimeout(() => {
      statusDiv.textContent = '';
    }, 2000);
  } catch (error) {
    console.error('Error:', error);
    statusDiv.textContent = '保存失败：' + (error.message || '未知错误');
  }
});

// 查看保存的数据
document.getElementById('viewSavedData').addEventListener('click', async () => {
  const savedDataDiv = document.getElementById('savedData');
  const statsDataDiv = document.getElementById('statsData');
  const statusDiv = document.getElementById('status');

  try {
    // 从服务器获取数据
    const response = await fetch('http://localhost:3000/api/pages');
    if (!response.ok) {
      throw new Error('服务器响应错误');
    }
    
    const result = await response.json();
    const savedPages = result.data || [];

    if (savedPages.length === 0) {
      savedDataDiv.innerHTML = '<p>暂无保存的数据</p>';
    } else {
      // 显示保存的数据
      const dataHtml = savedPages.map(page => `
        <div class="data-item">
          <strong>标题：</strong>${page.title}<br>
          <strong>URL：</strong><a href="${page.url}" target="_blank">${page.url}</a><br>
          <strong>保存时间：</strong>${new Date(page.timestamp).toLocaleString()}<br>
          <strong>描述：</strong>${page.description || '无'}<br>
        </div>
      `).join('');
      
      savedDataDiv.innerHTML = dataHtml;
    }

    // 显示数据区域
    savedDataDiv.style.display = 'block';
    statsDataDiv.style.display = 'none';
    statusDiv.textContent = '';
  } catch (error) {
    console.error('Error:', error);
    statusDiv.textContent = '获取数据失败：' + (error.message || '未知错误');
  }
});

// 查看数据库统计信息
document.getElementById('viewStats').addEventListener('click', async () => {
  const statsDataDiv = document.getElementById('statsData');
  const savedDataDiv = document.getElementById('savedData');
  const statusDiv = document.getElementById('status');

  try {
    // 从服务器获取统计数据
    const response = await fetch('http://localhost:3000/api/stats');
    if (!response.ok) {
      throw new Error('服务器响应错误');
    }
    
    const result = await response.json();
    const { totalRecords, latestRecords, databaseInfo } = result.stats;

    // 构建统计信息HTML
    let statsHtml = `
      <div class="stats-section">
        <h3>数据库概况</h3>
        <p>总记录数：${totalRecords}</p>
        <p>数据库大小：${databaseInfo ? databaseInfo['Size (MB)'] + ' MB' : '计算中...'}</p>
      </div>
    `;

    if (latestRecords && latestRecords.length > 0) {
      statsHtml += `
        <div class="stats-section">
          <h3>最新保存的页面</h3>
          ${latestRecords.map(record => `
            <div class="data-item">
              <strong>标题：</strong>${record.title}<br>
              <strong>URL：</strong><a href="${record.url}" target="_blank">${record.url}</a><br>
              <strong>保存时间：</strong>${new Date(record.timestamp).toLocaleString()}
            </div>
          `).join('')}
        </div>
      `;
    }

    // 显示统计信息
    statsDataDiv.innerHTML = statsHtml;
    statsDataDiv.style.display = 'block';
    savedDataDiv.style.display = 'none';
    statusDiv.textContent = '';
  } catch (error) {
    console.error('Error:', error);
    statusDiv.textContent = '获取统计信息失败：' + (error.message || '未知错误');
  }
}); 