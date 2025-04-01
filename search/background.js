// 监听来自弹出窗口的消息
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === 'saveToDatabase') {
    saveToDatabase(request.data);
  }
});

// 保存数据到服务器和本地存储
async function saveToDatabase(data) {
  try {
    // 保存到服务器
    const response = await fetch('http://localhost:3000/api/save', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data)
    });

    if (!response.ok) {
      throw new Error('服务器响应错误');
    }

    // 同时保存到本地存储作为备份
    const result = await chrome.storage.local.get('savedPages');
    const savedPages = result.savedPages || [];
    savedPages.push(data);
    await chrome.storage.local.set({ savedPages });
    
    console.log('数据保存成功:', data);
  } catch (error) {
    console.error('保存数据时出错:', error);
    throw error;
  }
} 