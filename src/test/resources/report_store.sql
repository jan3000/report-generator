INSERT INTO ar_data.config (c_id, c_config, c_created_at) VALUES
  (35411214, 'YT142G002-Q11', '2017-12-07 16:07:08.814+00'),
  (25411214, 'AB142G002-Q11', '2017-12-07 16:07:08.814+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.merchant(m_id, m_merchant, m_name, m_created_at) VALUES
  (1, 'zalando', '810d1d00-4312-43e5-bd31-d8373fdd24c8', '2017-12-07 16:07:08.814+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.retail_product(rp_id, rp_product_id, rp_sales_channel_id, rp_created_at) VALUES
  (184190839,'AJ142G002-Q11000L000','01924c48-49bb-40c2-9c32-ab582e6db6f4','2017-08-17 09:02:22.295534+00'),
  (184190330,'JT142G002-Q1102XL000','01924c48-49bb-40c2-9c32-ab582e6db6f4','2017-08-17 09:02:17.594292+00'),
  (284190839,'AB142G002-Q11000L000','01924c48-49bb-40c2-9c32-ab582e6db6f4','2017-08-17 09:02:22.295534+00'),
  (284190330,'AB142G002-Q1102XL000','01924c48-49bb-40c2-9c32-ab582e6db6f4','2017-08-17 09:02:17.594292+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.retail_product_metadata(rpm_retail_product_id, rpm_cause, rpm_version, rpm_eid, rpm_flow_id, rpm_config_id, rpm_first_available_at, rpm_evaluated_at, rpm_updated_at) VALUES
  (184190839,'stock',42,'6db25b85-6823-492c-954d-ad5aa5dc8844','50896b84-602a-4415-939c-6cde9d869537',35411214,'2017-12-07 16:07:11.798+00','2017-12-07 21:45:54.531+00','2017-12-07 21:46:01.935753+00'),
  (184190330,'product',44,'5b41caca-4dbb-4448-9a84-1ac436ae781e','hrIfX5Zu6xTHLuPlx3eRwt4S',35411214,'2017-12-07 16:07:08.814+00','2017-12-07 16:07:09.089+00','2017-12-07 16:07:14.253059+00'),
  (284190839,'stock',42,'6db25b85-6823-492c-954d-ad5aa5dc8844','50896b84-602a-4415-939c-6cde9d869537',25411214,'2017-12-07 16:07:11.798+00','2017-12-07 21:45:54.531+00','2017-12-07 21:46:01.935753+00'),
  (284190330,'product',44,'5b41caca-4dbb-4448-9a84-1ac436ae781e','hrIfX5Zu6xTHLuPlx3eRwt4S',25411214,'2017-12-07 16:07:08.814+00','2017-12-07 16:07:09.089+00','2017-12-07 16:07:14.253059+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.offer(o_id,o_retail_product_id,o_merchant_id,o_offer_version,o_failed_rules,o_status,o_selected,o_product_available,o_price_available,o_stock_available,o_updated_at,o_selection_rank,o_on_stock_since) VALUES
  (189676653,184190839,1,42,'[]','available','t','t','t','t','2017-12-07 21:46:01.935753+00',1,NULL),
  (189676148,184190330,1,44,'[]','available','t','t','t','t','2017-12-07 16:07:14.253059+00',1,NULL),
  (289676653,284190839,1,42,'[]','available','f','f','f','f','2017-12-07 21:46:01.935753+00',1,NULL),
  (289676148,284190330,1,44,'[]','available','f','f','f','f','2017-12-07 16:07:14.253059+00',1,NULL)
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.price(p_offer_id, p_price, p_version, p_eid, p_occurred_at, p_updated_at) VALUES
  (189676653, '{"start_date": "2017-09-08T06:37:54Z", "standard_price": {"amount": 24.95, "currency": "EUR"}}', 2, null, null,'2017-09-08 06:41:05.456409+00'),
  (189676148, '{"start_date": "2017-09-10T12:00:30Z", "standard_price": {"amount": 35.55, "currency": "EUR"}}', 2, null, null,'2017-09-09 10:00:00.000000+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.stock(s_offer_id, s_stock, s_count, s_version, s_eid, s_occurred_at, s_updated_at) VALUES
  (189676653,'{"stocks":[{"status":"available","quantity":50,"stock_id":"$Legacy$3$1"}],"quantities":[{"status":"available","quantity":50}]}',50,44,null,null,'2017-12-11 10:12:35.318101+00'),
  (189676148,'{"stocks":[{"status":"available","quantity":3,"stock_id":"$Legacy$3$1"}],"quantities":[{"status":"available","quantity":20}]}',50,44,null,null,'2017-12-30 10:00:00.000000+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.selection_tuple(st_offer_id, st_availability_weight, st_availability_weight_priority, st_platform_rank, st_platform_rank_priority, st_merchant_rank, st_merchant_rank_priority, st_price, st_price_priority, st_merchant_name, st_merchant_name_priority, st_updated_at) VALUES
  (189676653,0,0,0,1,2000,2,24.95,3,'810d1d00-4312-43e5-bd31-d8373fdd24c7',4,'2017-12-11 10:12:35.318101+00'),
  (189676148,1,1,1,1,2000,2,35.55,2,'810d1d00-4312-43e5-bd31-d8373fdd24c7',4,'2017-12-30 10:00:00.000000+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.content(c_retail_product_id, c_owner_id, c_content, c_version, c_eid, c_occurred_at, c_updated_at) VALUES
  (184190839, 1, '{"status": "active"}', 65, null, '2017-12-06 09:18:12.190049+00', '2017-12-06 09:18:12.190049+00'),
  (184190330, 1, '{"status": "disabled"}', 1, null, '2017-12-30 10:05:30.000000+00', '2017-12-30 10:05:30.000000+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.block_reason(br_id, br_code, br_description) VALUES
  (7, 'PBC23', 'Partner Program - Old Season')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.rule_type(rt_id, rt_name) VALUES
  (2, 'PRODUCT_CONFIG_BLOCKER')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.rule_definition(rd_id, rd_rule_id, rd_rule_context, rd_rule_type_id, rd_block_reason_id, rd_name, rd_description, rd_created_at) VALUES
  (196059, 'b790e1af-fbb9-4af0-a77c-5fe1514a32fb', 'SALES_CHANNEL', 2, 7, 'BM_RULE_MIGRTION_PRODUCT_CONFIG_BLOCKER', '21.03.2014-EPROD-25896-AKimmich', '2015-07-24 13:51:37.485155+00')
ON CONFLICT DO NOTHING;

INSERT INTO ar_data.rule_failure(rf_offer_id,rf_rule_definition_id,rf_details,rf_failing_since,rf_updated_at) VALUES
  (189676653, 196059, 'product is blocked by configId', NULL, '2017-12-11 09:56:09.192018+00')
ON CONFLICT DO NOTHING;




