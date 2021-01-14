--
-- PostgreSQL database dump
--

-- Dumped from database version 12.5
-- Dumped by pg_dump version 12.5

-- Started on 2020-12-15 00:05:23 MSK

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16660)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 2995 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 205 (class 1259 OID 16618)
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_email character(64) NOT NULL,
    user_login character(64) NOT NULL,
    user_password character(64) NOT NULL,
    user_role character(10),
    user_tariff integer
);


ALTER TABLE public."user" OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16635)
-- Name: Users_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Users_user_id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 204 (class 1259 OID 16594)
-- Name: file; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.file (
    file_id integer NOT NULL,
    file_name character(256) NOT NULL,
    file_size integer NOT NULL,
    file_user_id integer NOT NULL,
    file_uuid uuid[] NOT NULL,
    file_path character(256) NOT NULL
);


ALTER TABLE public.file OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16637)
-- Name: file_file_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.file ALTER COLUMN file_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.file_file_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 203 (class 1259 OID 16587)
-- Name: tariff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tariff (
    tariff_id integer NOT NULL,
    tariff_name character(50) NOT NULL,
    tariff_limit_mb integer NOT NULL
);


ALTER TABLE public.tariff OWNER TO postgres;

--
-- TOC entry 2986 (class 0 OID 16594)
-- Dependencies: 204
-- Data for Name: file; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.file (file_id, file_name, file_size, file_user_id, file_uuid, file_path) FROM stdin;
\.


--
-- TOC entry 2985 (class 0 OID 16587)
-- Dependencies: 203
-- Data for Name: tariff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tariff (tariff_id, tariff_name, tariff_limit_mb) FROM stdin;
1	50Mb                                              	50
2	250Mb                                             	250
3	1Gb                                               	1024
\.


--
-- TOC entry 2987 (class 0 OID 16618)
-- Dependencies: 205
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."user" (user_id, user_email, user_login, user_password, user_role, user_tariff) FROM stdin;
1	admin@admin.com                                                 	admin                                                           	$2y$10$vkIYJ2hp4g1TJZSsUzMcbO/Ra9ADxICAHfU3KKg2RZvuubmX0PXfS    	admin     	3
2	test@test.com                                                   	test                                                            	$2a$10$JPiFyhcO7TOcyrKC.HzS6.tI2wC3jQl2brNkBibVx.CNsKhRB9NzO    	user      	1
\.


--
-- TOC entry 2996 (class 0 OID 0)
-- Dependencies: 206
-- Name: Users_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Users_user_id_seq"', 2, true);


--
-- TOC entry 2997 (class 0 OID 0)
-- Dependencies: 207
-- Name: file_file_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.file_file_id_seq', 1, false);


--
-- TOC entry 2849 (class 2606 OID 16591)
-- Name: tariff Tariffes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tariff
    ADD CONSTRAINT "Tariffes_pkey" PRIMARY KEY (tariff_id);


--
-- TOC entry 2851 (class 2606 OID 16601)
-- Name: file file_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_pkey PRIMARY KEY (file_id);


--
-- TOC entry 2853 (class 2606 OID 16604)
-- Name: file file_uuid_constr; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.file
    ADD CONSTRAINT file_uuid_constr UNIQUE (file_uuid);


--
-- TOC entry 2857 (class 2606 OID 16634)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);


--
-- TOC entry 2854 (class 1259 OID 16605)
-- Name: fki_FK_user_file; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX "fki_FK_user_file" ON public.file USING btree (file_user_id);


--
-- TOC entry 2855 (class 1259 OID 16639)
-- Name: fki_user_tariff; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX fki_user_tariff ON public."user" USING btree (user_tariff);


--
-- TOC entry 2858 (class 2606 OID 16640)
-- Name: user user_tariff; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_tariff FOREIGN KEY (user_tariff) REFERENCES public.tariff(tariff_id);


-- Completed on 2020-12-15 00:05:23 MSK

--
-- PostgreSQL database dump complete
--

