import React from 'react';
import './App.css'; // ⭐ 방금 만든 CSS 파일을 여기서 불러옵니다!

function App() {
  return (
    <div className="app-container">
      
      {/* 📍 가짜 지도 배경 */}
      <div style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', zIndex: 10 }}>
        
        {/* ⭐ 내 현재 위치 (파란 점 + 레이더 파동) */}
        <div className="my-location-wrapper">
          <div className="radar-pulse"></div>
          <div className="core-dot"></div>
        </div>

        {/* 주변 가짜 마커들 */}
        <div className="marker-danger" style={{ top: '250px', left: '150px' }}></div> 
        <div className="marker-danger" style={{ top: '300px', left: '220px' }}></div>
        <div className="marker-warning" style={{ top: '400px', left: '250px' }}></div>
      </div>

      {/* ⬆️ 상단 영역 */}
      <div className="top-wrapper">
        <div className="search-bar">
          <span className="logo">이리로</span>
          <span className="search-text">안전 경로 탐색</span>
          <span className="search-icon">🔍</span>
        </div>
        <div className="filter-buttons">
          <button className="btn-filter btn-danger">⚠️ 위험 구역</button>
          <button className="btn-filter btn-safe">✅ 안전 구역</button>
        </div>
      </div>

      {/* ⬇️ 하단 영역 */}
      <div className="bottom-wrapper">
        <button className="btn-menu">
          <div className="menu-bar"></div>
          <div className="menu-bar"></div>
          <div className="menu-bar"></div>
        </button>
        <button className="btn-menu btn-report">
          <span>🚨</span>
          <span>신고</span>
        </button>
      </div>
      
    </div>
  );
}

export default App;