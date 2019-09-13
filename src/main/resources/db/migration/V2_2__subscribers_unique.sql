ALTER TABLE subscribers DROP CONSTRAINT subscribers_pkey;
ALTER TABLE subscribers ADD PRIMARY KEY (name);
ALTER TABLE subscribers ADD CONSTRAINT uniq_url UNIQUE (url);