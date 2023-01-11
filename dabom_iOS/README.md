# DaBom_iOS
> 인터넷 강의 플랫폼 개발  

## 23.01.04  
> ### gitHub
> - 연동 완료
> ### TabBar
> - 홈 - 내 강좌 - 마이페이지 구분 완료
> ### SideMenu
> - 카테고리 창 SideMenu 라이브러리 이용해 구현
> ### Tabman
> - 내 강좌에서 다봄 - 덜봄 - 찜 구현

## 23.01.05
> ### Home 화면
> - 메인 화면 레이아웃 잡기
> - TableView 안에 CollectionView 넣기
> - TableViewCell, CollectionViewCell Custom
> - 배너 관련 레이아웃 설계 -> TableView Header
> ### UIImage Content Mode
> - Aspect Fit, Aspect Fill, Scale To Fill
> ### 더미 데이터
> - 강좌 Thumbnail Image 4개
> ### gitHub
> - commit message

## 23.01.06
> ### Home 화면 Banner
> - 자동 스크롤 되는 배너 구현
> ### 카테고리 뷰 설정, 화면 이동
> - Home 화면 전체를 navigationController 위에 올림
> - 현재 카테고리 선택 후 화면 이동 시 깜빡임 현상

## 23.01.09
> ### 카테고리 뷰 변경
> - SideMenu 라이브러리 제거
> - Navigation Controller 이용
> ### 배너 및 강좌 선택 시 화면 변경
> - Navigation Controller로 화면 이동
> ### 강좌 상세 뷰 구성
> - 온/오프 버튼 추가
> - 찜하기 버튼 추가
> ### CollectionView Cell 크기 관련 이슈 해결
> - CollectionView Cell 크기가 설정되지 않는 이슈
> - CollectionView의 Estimate Size를 None으로 설정하여 해결

## 23.01.10
> ### 내 강좌 뷰 구성
> - 덜 본 강의, 다 본 강의, 찜한 강의 더미 데이터 뷰 구성 완료
> - 덜 본 강의, 다 본 강의 선택 시 강의 재생 페이지로 이동하도록 수정 필요
> ### Corner Radius와 Shadow가 함께 적용되지 않는 문제
> - Cell에 Shadow 적용, contentView에 Corner Radius 적용하여 해결

## 23.01.11
> ### 강좌 상세 뷰 구성
> - ScrollView + StackView 활용하여 뷰 구성
> - ScrollView의 스크롤 조정 필요
> - 랭킹 강좌 선택 시, 찜한 강좌 선택 시 재사용 완료
> ### 카테고리 뷰 구성
> - 대분류 - 소분류 기준으로 카테고리 구성