// 监听来自弹出窗口的消息
chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  if (request.action === 'getPageInfo') {
    try {
      // 收集页面信息
      const pageInfo = {
        title: document.title,
        url: window.location.href,
        description: getMetaDescription(),
        text: getMainContent(),
        timestamp: new Date().toISOString()
      };
      
      sendResponse(pageInfo);
    } catch (error) {
      console.error('Error collecting page info:', error);
      sendResponse({ error: error.message });
    }
  }
  return true; // 保持消息通道开放
});

// 获取页面描述
function getMetaDescription() {
  try {
    const metaDescription = document.querySelector('meta[name="description"]');
    return metaDescription ? metaDescription.getAttribute('content') : '';
  } catch (error) {
    console.error('Error getting meta description:', error);
    return '';
  }
}

// 获取主要内容
function getMainContent() {
  try {
    // 移除脚本标签和样式标签
    const content = document.body.cloneNode(true);
    const scripts = content.getElementsByTagName('script');
    const styles = content.getElementsByTagName('style');
    
    while (scripts.length > 0) {
      scripts[0].parentNode.removeChild(scripts[0]);
    }
    while (styles.length > 0) {
      styles[0].parentNode.removeChild(styles[0]);
    }
    
    return content.textContent.trim().replace(/\s+/g, ' ').substring(0, 1000);
  } catch (error) {
    console.error('Error getting main content:', error);
    return '';
  }
} 