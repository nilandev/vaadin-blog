CREATE UNIQUE INDEX post_slug_index ON post (slug);
CREATE UNIQUE INDEX category_slug_index ON category (slug);
CREATE UNIQUE INDEX tag_name_index ON tag (name);