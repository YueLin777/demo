 // 监听扩展安装事件
chrome.runtime.onInstalled.addListener(() => {
    console.log('扩展已安装');
  });
  
  // 监听来自弹出窗口的消息
  chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
    if (request.action === 'saveToDatabase') {
      console.log('收到保存请求:', request.data);
    }
  });