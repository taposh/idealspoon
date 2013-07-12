-- Adding start and stop nullable columns to ifb_ad table


ALTER TABLE ifb_ad ADD COLUMN start_date DATE;
ALTER TABLE ifb_ad ADD COLUMN stop_date DATE;