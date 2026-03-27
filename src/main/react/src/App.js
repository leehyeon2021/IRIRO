import React from 'react';

function App() {
  // 1. 전체 앱 컨테이너 (모바일 화면 크기 고정)
  const appStyle = {
    fontFamily: 'sans-serif',
    display: 'flex',
    flexDirection: 'column',
    height: '100vh',
    width: '100%',
    margin: 0,
    backgroundColor: '#e8eaf6', // 지도 배경을 살짝 푸른빛 도는 회색으로 변경
    position: 'relative',
    overflow: 'hidden',
  };

  // 2. 상단 영역 wrapper
  const topWrapperStyle = {
    position: 'absolute',
    top: '20px',
    left: '0',
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    zIndex: 100,
  };

  // 3. 둥근 검색바
  const searchBarStyle = {
    backgroundColor: 'white',
    width: '90%',
    maxWidth: '400px',
    height: '50px',
    borderRadius: '25px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    display: 'flex',
    alignItems: 'center',
    padding: '0 20px',
    marginBottom: '15px',
    boxSizing: 'border-box',
  };

  const logoStyle = { color: '#4caf50', fontWeight: 'bold', marginRight: '10px' };
  const searchTextStyle = { flex: 1, color: '#999' };
  const searchIconStyle = { fontSize: '20px', color: '#666' };

  // 4. 필터 버튼 영역
  const filterButtonsStyle = {
    display: 'flex',
    justifyContent: 'space-between',
    width: '90%',
    maxWidth: '400px',
  };

  const filterButtonStyle = {
    width: '48%',
    height: '50px',
    borderRadius: '10px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontWeight: 'bold',
    border: 'none',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    cursor: 'pointer',
  };

  const dangerButtonStyle = { ...filterButtonStyle, backgroundColor: '#ff5252', color: 'white' };
  const safeButtonStyle = { ...filterButtonStyle, backgroundColor: '#66bb6a', color: 'white' };

  // 5. 하단 영역 wrapper
  const bottomWrapperStyle = {
    position: 'absolute',
    bottom: '20px',
    left: '20px',
    right: '20px',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    zIndex: 100,
  };

  const menuButtonStyle = {
    backgroundColor: 'white',
    width: '60px',
    height: '60px',
    borderRadius: '50%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
    boxShadow: '0 4px 10px rgba(0, 0, 0, 0.2)',
    border: 'none',
    cursor: 'pointer',
  };

  const menuBarStyle = {
    width: '25px',
    height: '3px',
    backgroundColor: '#666',
    margin: '3px 0',
    borderRadius: '2px',
  };

  const reportButtonStyle = {
    ...menuButtonStyle,
    backgroundColor: '#ff4444',
    color: 'white',
    fontWeight: 'bold',
    fontSize: '14px',
  };

  // 6. 가짜 지도 마커 (주변 식당/위험구역 핀)
  const markerStyle = {
    position: 'absolute',
    width: '20px',
    height: '20px',
    borderRadius: '50%',
    zIndex: 50,
  };

  // ⭐ 7. 내 현재 위치 마커 스타일 (파란색 점 + 하얀 테두리 + 빛나는 효과)
  const myLocationStyle = {
    position: 'absolute',
    top: '60%', // 화면의 대략 중간보다 살짝 아래
    left: '30%', // 화면의 왼쪽 쯤 (피그마 이미지와 비슷한 위치)
    width: '22px',
    height: '22px',
    backgroundColor: '#4285F4', // 구글맵 스타일 파란색
    border: '3px solid white', // 하얀색 두꺼운 테두리
    borderRadius: '50%',
    boxShadow: '0 0 15px rgba(66, 133, 244, 0.6)', // 파란색 빛이 퍼지는 효과
    zIndex: 60, // 다른 핀들보다 살짝 위에 보이도록!
  };

  return (
    <div style={appStyle}>
      {/* 📍 가짜 지도 배경 */}
      <div style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', zIndex: 10 }}>
        
        {/* ⭐ 내 현재 위치 (파란색 빛나는 점) */}
        <div style={myLocationStyle}></div>

        {/* 주변 가짜 마커들 */}
        <div style={{ ...markerStyle, top: '250px', left: '150px', backgroundColor: '#ff5252' }}></div> 
        <div style={{ ...markerStyle, top: '300px', left: '220px', backgroundColor: '#ff5252' }}></div>
        <div style={{ ...markerStyle, top: '400px', left: '250px', backgroundColor: '#ff9800' }}></div>
      </div>

      {/* ⬆️ 상단 영역 */}
      <div style={topWrapperStyle}>
        <div style={searchBarStyle}>
          <span style={logoStyle}>이리로</span>
          <span style={searchTextStyle}>안전 경로 탐색</span>
          <span style={searchIconStyle}>🔍</span>
        </div>
        <div style={filterButtonsStyle}>
          <button style={dangerButtonStyle}>⚠️ 위험 구역</button>
          <button style={safeButtonStyle}>✅ 안전 구역</button>
        </div>
      </div>

      {/* ⬇️ 하단 영역 */}
      <div style={bottomWrapperStyle}>
        <button style={menuButtonStyle}>
          <div style={menuBarStyle}></div>
          <div style={menuBarStyle}></div>
          <div style={menuBarStyle}></div>
        </button>
        <button style={reportButtonStyle}>
          <span>🚨</span>
          <span>신고</span>
        </button>
      </div>
    </div>
  );
}

export default App;