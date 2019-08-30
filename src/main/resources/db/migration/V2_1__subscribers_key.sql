ALTER TABLE subscribers DROP CONSTRAINT subscribers_pkey;
ALTER TABLE subscribers DROP COLUMN id;
ALTER TABLE subscribers ADD PRIMARY KEY (url);