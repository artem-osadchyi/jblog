-- Add column "tag" to "post_tag" table
alter table post_tag add column tag varchar(50);

-- Copy values from "tag.name" table to "post_tag.tag"
update post_tag set tag = tag.name from tag where tag_id = tag.id;

-- Remove unnecessary column "tag_id" from table "post_tag"
alter table post_tag drop column tag_id;

-- Add not-null constraint on column "tag"
alter table post_tag alter column tag set not null;

-- Add unique constraint on table "post_tag"
alter table post_tag add unique(post_id, tag);

-- Rename "post_tag" table to "post_tags"
alter table post_tag rename to post_tags;

-- Remove unnecessary table "tag"
drop table tag;
