INSERT INTO biz(biz_name, biz_reg_num, biz_admin, biz_phone_num)
VALUES('jiwon_company', '19990321', '구지원', '010-5xxx-2xxx');

INSERT INTO member(biz_id, member_id, pwd, email, role)
VALUES(1, 'jiwon0321', '990321', 'jiwon@gmail.com', 'ADMIN');

INSERT INTO device(tid, mid, did, pv)
VALUES('A001', '6', '1', '5');

INSERT INTO car (mdn,biz_id,device_id,car_type,car_plate,car_year,purpose ,status,sum,created_at,updated_at,deleted_at) VALUES
 	 ('0488539469',1,1,'소나타','전북 62서 1207','2013','법인','running','23451','2020-05-16 14:30:46',NULL,NULL),
 	 ('0077184075',1,1,'아반떼','대전 51거 8511','2017','법인','running','25285','2022-09-14 09:03:08',NULL,NULL),
 	 ('6066499939',1,1,'포르쉐','대전 62서 2153','2020','렌트카','running','14213','2020-05-09 14:27:47',NULL,NULL),
 	 ('3475930872',1,1,'메르세데스','대구 21도로 5982','2024','렌트카','running','9592','2021-10-03 17:07:06',NULL,NULL),
 	 ('5492360692',1,1,'람보르기니','대구 79더 4119','2021','렌트카','fixing','23611','2023-09-12 13:59:15',NULL,NULL),
 	 ('6300450876',1,1,'벤츠','인천 86머 4334','2016','렌트카',null,'17491','2022-03-01 00:04:20',NULL,NULL),
 	 ('9805137474',1,1,'산타페','경북 16러 6297','2014','렌트카','fixing','9294','2025-01-02 13:23:23',NULL,NULL),
 	 ('4620865873',1,1,'카니발','경기 33서 1176','2019','카쉐어링','running','13771','2024-03-21 06:30:31',NULL,NULL);

INSERT INTO rent(rent_uuid, mdn, rent_stime, rent_etime, renter_name, renter_phone, purpose, rent_status, rent_loc, rent_lat, rent_lon, return_loc, return_lat, return_lon, created_at, updated_at)
VALUES ( 'some_uuid', '0488539469', -- 임의의 UUID
'2024-10-27 12:00:00',-- 임의의 시작 시간
'2025-03-28 22:40:00', -- 임의의 종료 시간
'홍길동', -- 임의의 사용자 이름
'01012345678', -- 임의의 전화번호
'출장', -- purpose는 NULL 허용
'대여중', -- 임의의 대여 상태
'미왕', -- rent_loc는 NULL 허용
0, -- rent_lat는 NULL 허용
0, -- rent_lon는 NULL 허용
'빌딩', -- return_loc는 NULL 허용
0, -- return_lat는 NULL 허용
0, -- return_lon는 NULL 허용
'2024-10-27 09:00:00', -- 임의의 생성 시간
NULL -- updated_at는 NULL 허용
);
