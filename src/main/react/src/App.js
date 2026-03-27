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
    backgroundColor: '#f2f2f2', // 실제 지도 들어갈 자리임을 보여주는 연회색 배경
    position: 'relative', // 중요! 하단 버튼 배치를 위해 필요합니다.
    overflow: 'hidden', // 스크롤 방지
  };

  // 2. 상단 영역 wrapper (검색바 + 필터 버튼)
  const topWrapperStyle = {
    position: 'absolute',
    top: '20px', // 스마트폰 상단 바 공간 고려
    left: '0',
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    zIndex: 100, // 지도보다 위에 떠있게 함
  };

  // 3. 둥근 검색바
  const searchBarStyle = {
    backgroundColor: 'white',
    width: '90%',
    maxWidth: '400px', // 너무 넓어지지 않게 방지
    height: '50px',
    borderRadius: '25px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)', // 살짝 그림자 효과
    display: 'flex',
    alignItems: 'center',
    padding: '0 20px',
    marginBottom: '15px',
    boxSizing: 'border-box', // 패딩 포함 크기 계산
  };

  // 검색바 내부 요소들
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

  // 개별 필터 버튼 기본 스타일
  const filterButtonStyle = {
    width: '48%', // 정확히 반씩 차지
    height: '50px',
    borderRadius: '10px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontWeight: 'bold',
    border: 'none', // 기본 버튼 테두리 제거
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)', // 살짝 그림자 효과
    cursor: 'pointer',
  };

  // 위험 구역 버튼 (빨간색)
  const dangerButtonStyle = {
    ...filterButtonStyle,
    backgroundColor: '#ff5252',
    color: 'white',
  };

  // 안전 구역 버튼 (초록색)
  const safeButtonStyle = {
    ...filterButtonStyle,
    backgroundColor: '#66bb6a',
    color: 'white',
  };

  // 5. 하단 영역 wrapper (메뉴 + 신고 버튼)
  const bottomWrapperStyle = {
    position: 'absolute',
    bottom: '20px',
    left: '20px',
    right: '20px',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    zIndex: 100, // 지도보다 위에 떠있게 함
  };

  // 가로 세 줄 메뉴 버튼
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

  // 메뉴 바 (세 줄)
  const menuBarStyle = {
    width: '25px',
    height: '3px',
    backgroundColor: '#666',
    margin: '3px 0',
    borderRadius: '2px',
  };

  // 신고 버튼 (빨간색 사이렌)
  const reportButtonStyle = {
    ...menuButtonStyle, // 공통 스타일 상속
    backgroundColor: '#ff4444',
    color: 'white',
    fontWeight: 'bold',
    fontSize: '14px',
  };

  // 6. 가짜 지도 마커 (왼쪽 스크린샷 핀들을 대략 흉내)
  const markerStyle = {
    position: 'absolute',
    width: '20px',
    height: '20px',
    borderRadius: '50%',
    zIndex: 50,
  };

  return (
    <div style={appStyle}>
      {/* 📍 가짜 지도 배경 (실제 지도가 들어올 영역) */}
      <div style={{ position: 'absolute', top: 0, left: 0, width: '100%', height: '100%', zIndex: 10 }}>
        {/* 가짜 마커 배치 (이미지의 핀들을 대략 흉내) */}
        <div style={{ ...markerStyle, top: '250px', left: '150px', backgroundColor: '#e91e63' }}></div> {/* 빨간 핀 */}
        <div style={{ ...markerStyle, top: '300px', left: '220px', backgroundColor: '#e91e63' }}></div>
        <div style={{ ...markerStyle, top: '350px', left: '180px', backgroundColor: '#e91e63' }}></div>
        <div style={{ ...markerStyle, top: '400px', left: '250px', backgroundColor: '#3f51b5' }}></div> {/* 파란 핀 */}
        <div style={{ ...markerStyle, top: '500px', left: '100px', backgroundColor: '#3f51b5' }}></div>
        <div style={{ ...markerStyle, top: '280px', left: '320px', backgroundColor: '#ff9800' }}></div> {/* 주황 핀 */}
      </div>

      {/* ⬆️ 상단 영역 (검색바 + 필터 버튼) */}
      <div style={topWrapperStyle}>
        {/* 둥근 검색바 */}
        <div style={searchBarStyle}>
          <span style={logoStyle}>이리로</span>
          <span style={searchTextStyle}>안전 경로 탐색</span>
          <span style={searchIconStyle}>🔍</span>
        </div>

        {/* 필터 버튼 2개 */}
        <div style={filterButtonsStyle}>
          <button style={dangerButtonStyle}>⚠️ 위험 구역</button>
          <button style={safeButtonStyle}>✅ 안전 구역</button>
        </div>
      </div>

      {/* ⬇️ 하단 영역 (메뉴 + 신고 버튼) */}
      <div style={bottomWrapperStyle}>
        {/* 가로 세 줄 메뉴 버튼 */}
        <button style={menuButtonStyle}>
          <div style={menuBarStyle}></div>
          <div style={menuBarStyle}></div>
          <div style={menuBarStyle}></div>
        </button>

        {/* 신고 버튼 */}
        <button style={reportButtonStyle}>
          <span>🚨</span>
          <span>신고</span>
        </button>
      </div>
    </div>
  );
}

export default App;