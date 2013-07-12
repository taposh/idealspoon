-- This script updates the Ad entity to remove product_name, question, product_destination

ALTER TABLE ifb_ad DROP COLUMN product_name, DROP COLUMN question, DROP COLUMN product_destination;
