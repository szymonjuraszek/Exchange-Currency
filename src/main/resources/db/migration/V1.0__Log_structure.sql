CREATE TABLE public.log
(
    id SERIAL PRIMARY KEY,
    message character varying ,
    "time" TIMESTAMP NOT NULL,
    uuid character varying NOT NULL
)
