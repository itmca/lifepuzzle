# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [CalVer](https://calver.org/).

## [1.1.0](https://github.com/itmca/lifepuzzle/compare/lifepuzzle-backend-v1.0.0...lifepuzzle-backend-v1.1.0) (2026-01-28)


### Features

* add database indexes for gallery API performance optimization ([#104](https://github.com/itmca/lifepuzzle/issues/104)) ([e5d16b6](https://github.com/itmca/lifepuzzle/commit/e5d16b621e70e62822a5bad9f884707ba4eec248))
* add HEIC image format support to DecodeImage ([#83](https://github.com/itmca/lifepuzzle/issues/83)) ([3c12218](https://github.com/itmca/lifepuzzle/commit/3c1221894fcad93ea6d739747545ead55c8dc12e))
* AI 드라이빙 비디오 정렬 기준 변경 및 DTO에 S3 서버 호스트 프리픽스 추가 ([#146](https://github.com/itmca/lifepuzzle/issues/146)) ([cc58b1e](https://github.com/itmca/lifepuzzle/commit/cc58b1e130f5e942487d2e8e88d2e19dc34f5d51))
* auth 및 facebook API에 v1 버전 추가 ([#131](https://github.com/itmca/lifepuzzle/issues/131)) ([4d3a6b1](https://github.com/itmca/lifepuzzle/commit/4d3a6b19744360f581eff5d4666804b52b7f881d))
* EXIF orientation 디버그 로그 추가 ([#124](https://github.com/itmca/lifepuzzle/issues/124)) ([fa3fdf8](https://github.com/itmca/lifepuzzle/commit/fa3fdf8894caa5ca52362c07ac9ecc2d8977c474))
* Facebook 사진 딥링크 리다이렉트 기능 추가 ([#152](https://github.com/itmca/lifepuzzle/issues/152)) ([477b52f](https://github.com/itmca/lifepuzzle/commit/477b52fcb8b58148638829009cfab4a4b2274ad8))
* FE 배포 후 deprecated API 제거 ([#140](https://github.com/itmca/lifepuzzle/issues/140)) ([a8d6003](https://github.com/itmca/lifepuzzle/commit/a8d6003a101605fb6bdd3a50572726eb1d05759a))
* image-resizer 데이터베이스 스키마를 gallery 테이블로 변경 ([#122](https://github.com/itmca/lifepuzzle/issues/122)) ([6ca122f](https://github.com/itmca/lifepuzzle/commit/6ca122f62d3862d1e4f2a38205171c8169a38965))
* JWT 인증 필터에서 /auth/ 경로 제외 처리 추가 ([#106](https://github.com/itmca/lifepuzzle/issues/106)) ([2ce25e9](https://github.com/itmca/lifepuzzle/commit/2ce25e987202f98d1c8c1bfae74e4003dc74994e))
* make S3_SERVER_HOST configurable via environment variable ([#101](https://github.com/itmca/lifepuzzle/issues/101)) ([4ef808f](https://github.com/itmca/lifepuzzle/commit/4ef808f180be149799128a437cabfe599a262f84))
* optimize /v1/galleries API performance ([#103](https://github.com/itmca/lifepuzzle/issues/103)) ([4b12cdc](https://github.com/itmca/lifepuzzle/commit/4b12cdcb468aa709510195dc8b3b57c8a58cd9a1))
* presigned URL 응답에 FE용 headers 정보 추가 ([#120](https://github.com/itmca/lifepuzzle/issues/120)) ([2e9ae50](https://github.com/itmca/lifepuzzle/commit/2e9ae501c47c1c7eff199d675d2d6f898ad626c8))
* skip Slack notifications in local/test profiles and Mac OS ([#38](https://github.com/itmca/lifepuzzle/issues/38)) ([4e5d727](https://github.com/itmca/lifepuzzle/commit/4e5d72769464e2ff9e2067cc2005cb182b2a41f7))
* Spring Boot Actuator 설정 추가 ([#119](https://github.com/itmca/lifepuzzle/issues/119)) ([39442ac](https://github.com/itmca/lifepuzzle/commit/39442ac4a4431f96418f619fe626d8565e6a9748))
* update image resizing constants to align with image-resizer service ([#102](https://github.com/itmca/lifepuzzle/issues/102)) ([e789756](https://github.com/itmca/lifepuzzle/commit/e789756c400bc2ec7ec94135d645607a480ad65e))
* 갤러리 업로드 완료 API 구현 ([#114](https://github.com/itmca/lifepuzzle/issues/114)) ([b28f504](https://github.com/itmca/lifepuzzle/commit/b28f504dd4eae99a1ac64127ee2a83cf55bd832e))
* 이미지 리사이징 시 단수 경로를 복수 경로로 자동 마이그레이션 ([#128](https://github.com/itmca/lifepuzzle/issues/128)) ([d2a8397](https://github.com/itmca/lifepuzzle/commit/d2a83974f2b43689dc14ec78c52252dd543eda79))


### Bug Fixes

* change temp directory to /tmp to resolve container permissions ([#68](https://github.com/itmca/lifepuzzle/issues/68)) ([d62775f](https://github.com/itmca/lifepuzzle/commit/d62775f3ee2e92decbfd7636bf934bee3d6b01db))
* Facebook OAuth 콜백 응답을 302 리다이렉트에서 200 OK로 변경 ([#153](https://github.com/itmca/lifepuzzle/issues/153)) ([b6087be](https://github.com/itmca/lifepuzzle/commit/b6087be0a8035c1aaf1de1e9684f761c07b21e68))
* FE 컨벤션에 맞춰 파일 경로를 단수형으로 수정 ([#130](https://github.com/itmca/lifepuzzle/issues/130)) ([a66e9d8](https://github.com/itmca/lifepuzzle/commit/a66e9d8db0a7a3b50dec753f6febd066da0fa357))
* FE 호환성을 위한 갤러리 조회 API 롤백 ([#142](https://github.com/itmca/lifepuzzle/issues/142)) ([5e57721](https://github.com/itmca/lifepuzzle/commit/5e57721f958e2decb98e09b29db8157826efb515))
* force pod replacement in image-resizer deployment ([#95](https://github.com/itmca/lifepuzzle/issues/95)) ([6930f54](https://github.com/itmca/lifepuzzle/commit/6930f54c047ea66380748fe6ae3ee088380da930))
* HEIC EXIF orientation 추출 로직 개선 ([#126](https://github.com/itmca/lifepuzzle/issues/126)) ([ccb0ae4](https://github.com/itmca/lifepuzzle/commit/ccb0ae4e2a5a9d8415780a842b6398ebaeb87fca))
* HEIC 파일에서 EXIF orientation 추출 지원 ([#125](https://github.com/itmca/lifepuzzle/issues/125)) ([1288a26](https://github.com/itmca/lifepuzzle/commit/1288a26da05f89268c9f30d133bacbf9c3cff37c))
* HikariCP 연결 풀 설정 최적화 ([#118](https://github.com/itmca/lifepuzzle/issues/118)) ([4c8cd1a](https://github.com/itmca/lifepuzzle/commit/4c8cd1a619f82abb7538d9e33daa2ce311784f1c))
* image-resizer에서 EXIF orientation 처리 추가 ([#121](https://github.com/itmca/lifepuzzle/issues/121)) ([9618477](https://github.com/itmca/lifepuzzle/commit/9618477288999cf221764cf1883d54ef73aef545))
* migrateSingularToPlural 함수에서 사용되지 않는 heroId 변수 제거 ([#129](https://github.com/itmca/lifepuzzle/issues/129)) ([5db0a6e](https://github.com/itmca/lifepuzzle/commit/5db0a6eeb09c0bd2c9c9ba6076a8995b63106707))
* move environment variables to runtime stage only ([#35](https://github.com/itmca/lifepuzzle/issues/35)) ([ecae3f4](https://github.com/itmca/lifepuzzle/commit/ecae3f462363a268e77c50eee8afcefd8db0b9d0))
* pass environment variables to Docker build for lifepuzzle-api ([#34](https://github.com/itmca/lifepuzzle/issues/34)) ([17874cf](https://github.com/itmca/lifepuzzle/commit/17874cf32ca03556f363d38fc44327d24fc85ec5))
* processMessage 함수에 EXIF 방향 처리 추가 ([#123](https://github.com/itmca/lifepuzzle/issues/123)) ([2179564](https://github.com/itmca/lifepuzzle/commit/21795644d182a60238075b0a7f1372bce492ad19))
* rename flyway file ([#110](https://github.com/itmca/lifepuzzle/issues/110)) ([7d3f18b](https://github.com/itmca/lifepuzzle/commit/7d3f18b1093c2cd6c5e0a9fbbdbf1c001b660730))
* SecurityConfig에 v1 auth 경로 추가 ([#134](https://github.com/itmca/lifepuzzle/issues/134)) ([aed401c](https://github.com/itmca/lifepuzzle/commit/aed401c27abb86f907c1f677a6adb2f7556e50e1))
* StoryWriteEndpoint에서 Long 타입 비교를 equals로 변경 ([#132](https://github.com/itmca/lifepuzzle/issues/132)) ([4574101](https://github.com/itmca/lifepuzzle/commit/45741018f71dbc616fb1de624b072b2143ac7188))
* use configurable workspace dir in deploy-image-resizer workflow ([#39](https://github.com/itmca/lifepuzzle/issues/39)) ([cb33549](https://github.com/itmca/lifepuzzle/commit/cb33549a9a5dacc4bf045aa97de1eaa66675e731))
* use proper multi-stage Dockerfile for lifepuzzle-api deployment ([#33](https://github.com/itmca/lifepuzzle/issues/33)) ([7122efb](https://github.com/itmca/lifepuzzle/commit/7122efbc5cf66a0e2e5686dfbca507187e90bec9))

## [Unreleased]

## 2023.05_1

### Added

- Arabic translation (#444).
- v1.1 French translation.
