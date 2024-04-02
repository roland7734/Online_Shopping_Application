--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: brands; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.brands (
    brandid integer NOT NULL,
    brandname character varying(50) NOT NULL
);


ALTER TABLE public.brands OWNER TO postgres;

--
-- Name: cart; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cart (
    cartid integer NOT NULL,
    userid integer,
    productid integer,
    quantity integer NOT NULL,
    CONSTRAINT cart_check CHECK ((quantity > 0))
);


ALTER TABLE public.cart OWNER TO postgres;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    categoryid integer NOT NULL,
    categoryname character varying(50) NOT NULL
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customers (
    firstname character varying,
    lastname character varying,
    userid integer NOT NULL,
    address character varying,
    phone character varying,
    email character varying,
    CONSTRAINT check_firstname_letters_only CHECK (((firstname)::text ~ '^[A-Za-z]+$'::text)),
    CONSTRAINT check_lastname_letters_only CHECK (((lastname)::text ~ '^[A-Za-z]+$'::text)),
    CONSTRAINT customers_check CHECK ((length((phone)::text) = 10)),
    CONSTRAINT valid_email_format CHECK (((email)::text ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'::text))
);


ALTER TABLE public.customers OWNER TO postgres;

--
-- Name: manufacturers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.manufacturers (
    manufacturerid integer NOT NULL,
    manufacturername character varying(100) NOT NULL,
    address character varying(255),
    website character varying(255),
    contactemail character varying(100),
    contactphone character varying,
    CONSTRAINT check_contactemail_format CHECK (((contactemail)::text ~ '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'::text)),
    CONSTRAINT check_contactphone_format CHECK (((contactphone)::text ~ '^\d{3}-\d{3}-\d{4}$'::text)),
    CONSTRAINT check_website_format CHECK (((website)::text ~ '^www\.[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$'::text))
);


ALTER TABLE public.manufacturers OWNER TO postgres;

--
-- Name: orderitems; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderitems (
    orderid integer NOT NULL,
    productid integer NOT NULL,
    quantity integer NOT NULL,
    subtotal numeric(10,2) NOT NULL,
    CONSTRAINT check_quantity_positive CHECK ((quantity > 0)),
    CONSTRAINT check_subtotal_positive CHECK ((subtotal > (0)::numeric))
);


ALTER TABLE public.orderitems OWNER TO postgres;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    orderid integer NOT NULL,
    userid integer,
    address character varying,
    vouchername character varying(50),
    orderdate timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    totalamount numeric(10,2) NOT NULL,
    status character varying(20) DEFAULT 'Pending'::character varying NOT NULL,
    totalamountbeforediscount numeric(10,2),
    CONSTRAINT check_order_status CHECK (((status)::text = ANY ((ARRAY['Pending'::character varying, 'Out for Delivery'::character varying, 'Delivered'::character varying, 'Order Confirmed'::character varying, 'Shipped'::character varying])::text[]))),
    CONSTRAINT check_totalamount_positive CHECK ((totalamount > (0)::numeric)),
    CONSTRAINT check_totalamountbeforediscount CHECK (((totalamountbeforediscount >= (0)::numeric) AND (totalamountbeforediscount >= totalamount)))
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payments (
    paymentid integer NOT NULL,
    orderid integer,
    paymentdate timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    amount numeric(10,2) NOT NULL,
    paymentmethod character varying(50) NOT NULL,
    transactionid character varying(100) NOT NULL,
    CONSTRAINT check_amount_positive CHECK ((amount > (0)::numeric)),
    CONSTRAINT check_paymentmethod CHECK (((paymentmethod)::text = ANY ((ARRAY['Credit Card'::character varying, 'Cash on Delivery'::character varying])::text[])))
);


ALTER TABLE public.payments OWNER TO postgres;

--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    productid integer NOT NULL,
    productname character varying(100) NOT NULL,
    categoryid integer,
    brandid integer,
    price numeric(10,2) NOT NULL,
    stockquantity integer NOT NULL,
    imageurl character varying(255),
    description text,
    manufacturerid integer,
    isdeleted boolean DEFAULT false,
    CONSTRAINT check_price_positive CHECK ((price > (0)::numeric)),
    CONSTRAINT check_stockquantity_non_negative CHECK ((stockquantity >= 0))
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    userid integer NOT NULL,
    username character varying,
    password character varying,
    type_user character varying,
    CONSTRAINT check_type_user_values CHECK (((type_user)::text = ANY ((ARRAY['admin'::character varying, 'customer'::character varying])::text[])))
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: vouchers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vouchers (
    vouchercode character varying(50) NOT NULL,
    discountpercentage integer NOT NULL,
    startdate date,
    enddate date,
    isactive boolean NOT NULL,
    CONSTRAINT check_discountpercentage_range CHECK (((discountpercentage >= 1) AND (discountpercentage <= 100))),
    CONSTRAINT check_startdate_enddate CHECK (((startdate IS NULL) OR (enddate IS NULL) OR (startdate <= enddate)))
);


ALTER TABLE public.vouchers OWNER TO postgres;

--
-- Data for Name: brands; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.brands (brandid, brandname) FROM stdin;
1	Samsung
2	Apple
3	Sony
4	LG
5	HP
6	Dell
7	Lenovo
8	Bose
9	Panasonic
10	Philips
11	HomeMaster
\.


--
-- Data for Name: cart; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cart (cartid, userid, productid, quantity) FROM stdin;
8	146	200357	1
4	117	2	2
1	137	6	1
2	137	10	1
3	137	4	1
9	146	73568	1
\.


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (categoryid, categoryname) FROM stdin;
1	Phones
2	Laptops
3	TVs
4	Tablets
5	Kitchen Appliances
6	Consumer Electronics
7	Gaming
\.


--
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customers (firstname, lastname, userid, address, phone, email) FROM stdin;
Hossam	Mohsen	103	Greece, Athens, Oak Street, nr. 35	0432685424	hossam.mohsen@yahoo.com
Zarra	Elenith	117	Bulgaria, Sofia, Parris Street, nr. 22	0678932466	zarra.elenith45@hotmail.com
Miu	Enne	112	France, Lyon, Neighbourhood Street	0987654321	miu.enne@yahoo.com
Elena	Vladimirescu	186	Germany, Berlin, Austrich Street, nr.43	0928341001	elena.vladimir@mail.com
Anna	Marie	199	USA, San Andreas, Italy Street, nr. 55	0112123134	an.a@email.com
Anna	Crom	146	Hungary, Balaton, Green Street, nr. 45	0342535244	crom.anna@mail.uk
Roland	Austerbury	1	Germany, Berlin, Vaizer Street, nr. 53	0987654321	auster.bury@mail.com
Lorena	Buzea	165	Romania	0098775432	lorena.buzea@gmail.com
a	a	137	11	0123456789	aaa@aaa.aa
Roland	Anaa	177	rgtyh	1234567890	fcw.vftemir@mail.com
\.


--
-- Data for Name: manufacturers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.manufacturers (manufacturerid, manufacturername, address, website, contactemail, contactphone) FROM stdin;
3	Sony Corporation	456 Tech Avenue, Tokyo, Japan	www.sony.com	info@sony.com	456-789-0123
4	LG Electronics Inc.	789 Tech Boulevard, Seoul, South Korea	www.lg.com	info@lg.com	789-012-3456
5	HP Inc.	321 PC Lane, Palo Alto, CA, USA	www.hp.com	info@hp.com	234-567-8901
7	Lenovo Group Limited	890 Innovation Avenue, Beijing, China	www.lenovo.com	info@lenovo.com	890-123-4567
8	Bose Corporation	123 Acoustic Lane, Framingham, MA, USA	www.bose.com	info@bose.com	123-456-7890
1	Samsung Electronics Co., Ltd.	123 Tech Street, Seoul, South Korea	www.samsung.com	info@samsung.com	123-321-7890
2	Apple Inc.	1 Infinite Loop, Cupertino, CA, USA	www.apple.com	info@apple.com	987-897-3210
9	Panasonic Corporation	456 Innovation Street, Osaka, Japan	www.panasonic.com	info@panasonic.com	456-132-0123
10	Philips Electronics N.V.	789 Tech Square, Amsterdam, Netherlands	www.philips.com	info@philips.com	385-012-3456
6	Dell Technologies Inc.	456 PC Avenue, Round Rock, TX, USA	www.dell.com	info@dell.com	274-789-0123
11	HomeMaster Solutions	123 Main Street, Cityville, CA, USA	www.homemastersolutions.com	info@homemastersolutions.com	337-476-3822
12	Hon Hai Precision Industry Co., Ltd.	545 Precision Street, Beijing, China	www.foxconn.com	info@foxconn.com	445-535-2445
\.


--
-- Data for Name: orderitems; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orderitems (orderid, productid, quantity, subtotal) FROM stdin;
1934284	1	1	799.99
2011	1	2	1599.98
2011	2	1	1299.99
1348882	2	1	1299.99
1348882	4	1	699.99
1348882	1	2	1599.98
1568535	9	1	129.99
1568535	2	1	1299.99
1568535	4	2	1399.98
1918018	2	1	1299.99
1918018	1	2	1599.98
1558396	1	2	1599.98
1120859	4	1	699.99
1120859	5	1	899.99
1120859	3	2	3999.98
1120859	1	2	1599.98
1120859	6	2	4999.98
1288257	1	2	1599.98
1288257	3	1	1999.99
1499556	10	1	399.99
1499556	551074	1	72.99
1394605	4	1	699.99
1394605	9	2	259.98
1845380	551074	2	145.96
1845380	579678	1	149.99
1324379	5	1	899.99
1986701	2	2	2799.98
1986701	5	2	1799.98
1986701	579678	2	299.98
1986701	6	3	7499.97
1964671	73568	2	781.98
1964671	200357	3	1507.65
1020688	73568	2	781.98
1986721	144377	1	804.85
1986721	73568	2	781.98
1986721	16	1	799.99
1383465	37	1	999.99
1383465	2	6	8399.94
1207876	12	4	3999.96
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (orderid, userid, address, vouchername, orderdate, totalamount, status, totalamountbeforediscount) FROM stdin;
1934284	103	Australia, Victoria, Pine Road, nr. 101	\N	2023-12-28 18:27:32.481372	2399.97	Pending	2399.97
2011	103	Japan, Tokyo, Sakura Lane, nr. 333	\N	2023-12-28 20:49:22.807061	3699.96	Pending	3699.96
1918018	103	Brazil, Sao Paulo, Palm Boulevard, nr. 555	\N	2023-12-29 17:29:41.682874	2899.97	Pending	2899.97
1558396	103	France, Paris, Lavender Avenue, nr. 777	\N	2023-12-31 01:47:41.990108	1599.98	Pending	1599.98
1324379	103	Mexico, Mexico City, Cactus Drive, nr. 999	\N	2024-01-06 00:59:14.56059	899.99	Pending	899.99
1845380	103	China, Beijing, Kim Street, nr.44	CHINA20	2024-01-02 17:17:12.493705	236.76	Pending	441.91
1348882	103	Hungary, Budapest, Timber Street, nr. 453	A69	2023-12-28 21:01:35.378254	2699.97	Pending	3599.96
1568535	103	Serbia, Belgrade, Oat Street, nr.45	C33	2023-12-28 21:05:35.275772	1896.07	Pending	2829.97
1394605	112	Estonia, Crater, Abundece Street, nr. 3	AF54	2024-01-01 19:19:53.55939	719.98	Delivered	959.97
1986721	103	South Africa, Gauteng, Sunflower Close, nr. 888\n	\N	2024-01-09 15:13:11.134022	2386.82	Order Confirmed	2386.82
1964671	146	Hungary, Boloton, Strawberries Street, nr. 53	XMAS21	2024-01-06 23:23:16.600115	1259.30	Order Confirmed	2289.63
1288257	103	Netherlands, Amsterdam, 123 Main Street, 1012 AB	\N	2023-12-31 22:16:27.450868	3599.97	Order Confirmed	3599.97
1383465	165	caminul 7	HALFPR1CED	2024-01-09 17:01:48.829382	4699.97	Order Confirmed	9399.93
1120859	103	USA, Mississippi, Beach Street, nr. 1	\N	2023-12-31 17:54:31.237795	12199.92	Order Confirmed	12199.92
1207876	103	fifd	2T35G	2024-01-09 17:55:11.90227	1839.98	Delivered	3999.96
1499556	103	North Macedonia, Struga, Ocean Street, nr.44	\N	2024-01-01 19:09:05.873631	472.98	Shipped	472.98
1986701	146	Hungary, Boloton, Andreas Street, nr. 564	ASAQ31	2024-01-06 22:18:03.121557	11159.92	Order Confirmed	12399.91
1020688	146	USA, New York, Rose Avenue, nr. 53	\N	2024-01-07 00:21:52.735503	781.98	Pending	781.98
\.


--
-- Data for Name: payments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.payments (paymentid, orderid, paymentdate, amount, paymentmethod, transactionid) FROM stdin;
2	1348882	2023-12-28 21:01:36.15136	2699.97	Credit Card	TR202312282101361513602
3	1568535	2023-12-28 21:05:36.002453	1896.07	Credit Card	TR2023122821053600245302
4	1558396	2023-12-31 01:47:42.776624	1599.98	Credit Card	TR2023123101474277662402
5	1288257	2023-12-31 22:16:28.601599	3599.97	Credit Card	TR2023123122162860159902
6	1499556	2024-01-01 19:09:05.906989	472.98	Credit Card	TR2024010119090590698902
7	1845380	2024-01-02 17:17:12.524945	236.76	Credit Card	TR2024010217171252494502
9	1986701	2024-01-06 22:18:03.15048	11159.92	Credit Card	TR202401062218031504802
10	1964671	2024-01-06 23:23:16.624291	1259.30	Credit Card	TR2024010623231662429102
11	1020688	2024-01-07 00:21:52.778076	781.98	Credit Card	TR2024010700215277807602
1	1986721	2024-01-09 15:13:11.153368	2386.82	Credit Card	TR2024010915131115336802
8	1383465	2024-01-09 17:01:48.855134	4699.97	Credit Card	TR2024010917014885513402
12	1207876	2024-01-09 17:55:11.946385	1839.98	Credit Card	TR2024010917551194638502
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (productid, productname, categoryid, brandid, price, stockquantity, imageurl, description, manufacturerid, isdeleted) FROM stdin;
410971	Samsung S12	1	1	730.99	324	samsung-s12.jfif.jpg	Cutting-edge design with great camera capabilities.	1	f
551074	Philips High-Speed Blender	5	10	72.98	29	phillips_blender.jpg	Ultimate blending performance. With multiple speed settings and durable stainless steel blades, a versatile addition to your kitchen.	10	f
579678	HomeMaster - Vacuum Cleaner	6	11	149.99	47	home_master_vacuum.jpg	Designed for powerful suction and versatility, this vacuum cleaner is equipped with advanced filtration to capture dust and allergens.	11	f
5	HP Envy x360	2	5	899.99	36	hp_envy_laptop.jpg	Convertible laptop with AMD Ryzen processor.	5	f
6	Dell Alienware Gaming PC	7	6	2499.99	10	dell_alienware_pc.jpg	High-performance gaming PC with Alienware design.	6	f
7	Lenovo Tab P11	4	7	349.99	60	lenovo_tab.jpg	Versatile tablet for work and entertainment.	7	f
8	Bose SoundLink Revolve+	6	8	199.99	50	bose_speaker.jpg	Portable Bluetooth speaker with 360-degree sound.	8	f
1	Samsung Galaxy S21	1	1	799.99	48	samsung_galaxy_s21.jpg	High-end smartphone with powerful camera features.	1	f
3	Sony Bravia 4K OLED TV	3	3	1999.99	19	sony_bravia_tv.jpg	Premium 4K OLED TV with stunning visuals.	3	f
10	Philips 27" Gaming Monitor	7	10	399.99	29	philips_gaming_monitor.jpg	Immersive gaming monitor with high refresh rate.	10	f
4	LG Dishwasher	5	4	699.99	39	lg_dishwasher.jpg	Efficient dishwasher with multiple wash cycles.	4	f
9	Panasonic Microwave Oven	5	9	129.99	79	panasonic_microwave.jpg	Compact microwave oven for quick and easy cooking.	9	f
40	Dell XPS 13	2	6	1199.99	18	noimg.jpg	Award-winning laptop with InfinityEdge display for immersive experience.	6	t
809131	Philips Espresso Maker	5	10	57.35	45	expressor.jpg	This Deluxe Edition brings sophistication to your kitchen, allowing you to craft rich and aromatic espresso beverages.	10	f
952220	HomeMaster Robotic Vacuum	6	11	455.95	65	vacum.jpg	This intelligent vacuum navigates your home, cleaning efficiently and effectively.	11	f
39	HP Spectre x360	2	5	1299.99	15	hp_spectre_x360.jpg	Versatile 2-in-1 laptop with stunning design and powerful specs.	5	f
43	LG NanoCell 8K TV	3	4	2499.99	8	LG NanoCell 8K TV.jpg	Next-level 8K resolution with NanoCell technology for lifelike visuals.	4	f
44	Philips Smart LED TV	3	10	799.99	20	Philips Smart LED TV.jpg	Smart TV with Ambilight technology for a unique viewing experience.	10	f
45	Samsung Frame TV	3	1	1299.99	12	Samsung Frame TV.jpg	Art-inspired TV that transforms into a work of art when not in use.	1	f
36	Samsung Galaxy Book Pro	2	1	1099.99	30	laptop_galaxy_boo_pro.jpg	Slim and powerful laptop for productivity on the go.	1	f
11	Samsung Galaxy S40	1	1	1799.99	25	Samsung Galaxy S40.jfif.jpg	Flagship phone with powerful camera and stunning display.	1	f
200357	Apple iPhone 12 Pro	1	2	502.45	297	descărcare (4).jfif.jpg	Experience the power of an iPhone in a compact design.	2	t
13	Sony Xperia 5 III	1	3	899.99	18	Sony Xperia 5 III.jfif.jpg	Compact phone with pro-level camera capabilities and 120Hz display.	3	f
41	Samsung QLED 4K TV	3	1	1499.99	15	Samsung QLED 4K TV.jpg	Crystal clear 4K display with QLED technology for vibrant colors.	1	f
42	Sony BRAVIA OLED TV	3	3	1999.99	10	Sony BRAVIA OLED TV.jpg	Immersive OLED display with Acoustic Surface Audio+ for cinematic experience.	3	f
38	Sony VAIO E15	2	3	849.99	20	laptop_sony.jpg	Stylish laptop with vibrant display and long battery life.	3	f
144377	Bose Wireless Soundbar 700	6	8	804.85	54	soundbar.jpg	This sleek and powerful soundbar delivers clear dialogue, impressive bass, and a wide soundstage.	8	f
73568	Apple iPhone SE (2nd generation)	1	2	390.99	44	descărcare (3).jfif.jpg	Delivers impressive performance and camera capabilities.	2	f
37	Apple MacBook Air	2	2	999.99	24	laptop_apple_macbook_air.jpg	Ultra-thin design with Apple M1 chip for efficient performance.	2	f
2	Apple MacBook Pro 13	2	2	1399.99	19	macbook_pro.jpg	Professional-grade laptop with Retina display.	2	f
12	Apple iPhone 13	1	2	999.99	16	Apple iPhone 13.jpg	Latest iPhone with A15 Bionic chip for exceptional performance.	2	t
30	Philips Hue Smart Lighting Kit	6	10	149.99	25	noimg.jpg	Smart lighting kit for creating customizable atmospheres at home.	10	t
24	Panasonic Microwave Oven	5	9	149.99	30	noimg.jpg	Compact microwave oven with inverter technology for even cooking.	9	t
34	Lenovo Legion Gaming Monitor	7	7	1299.99	18	Lenovo Legion Gaming Laptop.jfif.jpg	Gaming laptop with powerful specs for an immersive gaming experience.	7	f
16	Apple iPad	4	2	799.99	29	Apple iPad Pro.jpg	Powerful iPad with M1 chip, perfect for creative tasks.	2	f
26	Bose QuietComfort 35 II	6	8	299.99	15	Bose QuietComfort 35 II.jfif.jpg	Premium noise-canceling headphones for an immersive audio experience.	8	f
29	Dell 27-inch 4K Monitor	6	6	599.99	20	noimg.jpg	High-resolution monitor with HDR for exceptional visual clarity.	6	t
25	HomeMaster Coffee Maker	5	11	79.99	25	HomeMaster Coffee Maker.jfif.jpg	Durable coffee maker for brewing the perfect cup every morning.	11	f
15	HP Elite x3	1	5	599.99	15	HP Elite x3.jfif.jpg	Business-focused phone with Windows 10 for productivity.	5	f
18	Lenovo Tab P11 Pro	4	7	499.99	20	Lenovo_tab_p11_pro.jfif.jpg	Premium Android tablet with OLED display and quad speakers.	7	f
23	LG InstaView Range	5	4	1599.99	12	LG InstaView Range.jfif.jpg	Smart oven with InstaView technology for checking cooking progress.	4	f
33	LG UltraGear 27-inch Gaming Monitor	7	4	399.99	15	LG UltraGear 27-inch Gaming Monitor.jfif.jpg	Fast and responsive gaming monitor for a competitive edge.	4	f
32	Microsoft Xbox Series X	7	5	499.99	12	Microsoft Xbox Series X.jfif.jpg	High-performance gaming console with backward compatibility.	5	f
14	LG Velvet 5G	1	4	699.99	22	LG Velvet 5G.jfif.jpg	Sleek 5G phone with dual-screen option for multitasking.	4	f
20	Philips 2-in-1 Android Tablet	4	10	449.99	18	Philips 2-in-1 Android Tablet.jfif.jpg	Convertible tablet with detachable keyboard for productivity on the go.	10	f
22	Philips Airfryer XXL	5	10	199.99	20	Philips Airfryer XXL.jfif.jpg	Large-capacity air fryer for healthier cooking with less oil.	10	f
21	Samsung Family Hub Refrigerator	5	1	2499.99	8	Samsung Family Hub Refrigerator.jfif.jpg	Smart refrigerator with touchscreen and food management features.	1	f
17	Samsung Galaxy Tab S7	4	1	649.99	25	Samsung Galaxy Tab S7.jfif.jpg	Versatile Android tablet with S Pen and high-refresh-rate display.	1	f
19	Sony Xperia Tablet Z4	4	3	799.99	15	Sony Xperia Tablet Z4.jfif.jpg	Slim and waterproof tablet with 2K display for entertainment.	3	f
35	Samsung Odyssey G9 Gaming Monitor	7	1	1499.99	8	Samsung Odyssey G9 Gaming Monitor.jfif.jpg	Curved gaming monitor with high refresh rate for ultimate gaming.	1	f
31	Sony PlayStation 5	7	3	499.99	10	Sony PlayStation 5.jfif.jpg	Next-gen gaming console with powerful hardware for immersive gaming.	3	f
27	Sony WH-1000XM4	6	3	349.99	12	Sony WH-1000XM4.jfif.jpg	Industry-leading wireless noise-canceling headphones with touch controls.	3	f
28	Samsung Soundbar HW-Q950A	6	1	1299.99	18	Samsung Soundbar HW-Q950A.jfif.jpg	Dolby Atmos soundbar for a cinematic audio experience at home.	1	f
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (userid, username, password, type_user) FROM stdin;
103	hossam72	1234	customer
117	zarra45	qwer	customer
2	anna.smith	postgres	admin
1	roland123	postgres	customer
112	miu11	1	customer
186	elena.vlad	aqa	customer
137	1	1	customer
199	anna.marie	ande	customer
146	cromanna	1	customer
102	jshdaj	s	customer
165	12	12	customer
177	hossa,m72	33	customer
\.


--
-- Data for Name: vouchers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vouchers (vouchercode, discountpercentage, startdate, enddate, isactive) FROM stdin;
B21	10	2023-12-21	2024-01-01	f
A69	25	2023-12-20	2023-12-29	f
CHINA20	20	2023-11-01	2024-02-02	f
ABCD2	12	2024-01-01	2024-01-03	f
C33	33	2023-12-22	2024-02-02	f
XMAS21	45	2024-01-03	2024-01-31	f
AF54	25	2023-12-01	2024-12-06	f
ANNA35	35	2024-01-09	2024-01-31	t
T0DAY	20	2024-01-09	2024-01-09	f
HALFPR1CED	50	2024-01-04	2024-01-27	f
AAAA122	20	2024-01-06	2025-01-05	t
2T35G	54	2024-01-06	2024-01-31	f
ASAQ31	10	2024-01-04	2024-12-04	f
\.


--
-- Name: brands brands_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.brands
    ADD CONSTRAINT brands_pkey PRIMARY KEY (brandid);


--
-- Name: cart cart_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_pkey PRIMARY KEY (cartid);


--
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (categoryid);


--
-- Name: customers customers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customers_pk PRIMARY KEY (userid);


--
-- Name: manufacturers manufacturers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.manufacturers
    ADD CONSTRAINT manufacturers_pkey PRIMARY KEY (manufacturerid);


--
-- Name: orderitems orderitems_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitems
    ADD CONSTRAINT orderitems_pkey PRIMARY KEY (orderid, productid);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (orderid);


--
-- Name: payments payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (paymentid);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (productid);


--
-- Name: customers unique_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT unique_email UNIQUE (email);


--
-- Name: payments unique_transactionid; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT unique_transactionid UNIQUE (transactionid);


--
-- Name: users unique_username; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT unique_username UNIQUE (username);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pk PRIMARY KEY (userid);


--
-- Name: vouchers vouchers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vouchers
    ADD CONSTRAINT vouchers_pk PRIMARY KEY (vouchercode);


--
-- Name: cart cart_productid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_productid_fkey FOREIGN KEY (productid) REFERENCES public.products(productid);


--
-- Name: cart cart_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cart
    ADD CONSTRAINT cart_userid_fkey FOREIGN KEY (userid) REFERENCES public.customers(userid);


--
-- Name: customers customer_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT customer_fk FOREIGN KEY (userid) REFERENCES public.users(userid);


--
-- Name: products fk_manufacturerid; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT fk_manufacturerid FOREIGN KEY (manufacturerid) REFERENCES public.manufacturers(manufacturerid);


--
-- Name: orderitems orderitems_orderid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitems
    ADD CONSTRAINT orderitems_orderid_fkey FOREIGN KEY (orderid) REFERENCES public.orders(orderid) ON DELETE CASCADE;


--
-- Name: orderitems orderitems_productid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderitems
    ADD CONSTRAINT orderitems_productid_fkey FOREIGN KEY (productid) REFERENCES public.products(productid);


--
-- Name: orders orders_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_fk FOREIGN KEY (vouchername) REFERENCES public.vouchers(vouchercode);


--
-- Name: orders orders_userid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_userid_fk FOREIGN KEY (userid) REFERENCES public.customers(userid) ON DELETE CASCADE;


--
-- Name: payments payments_orderid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_orderid_fkey FOREIGN KEY (orderid) REFERENCES public.orders(orderid) ON DELETE CASCADE;


--
-- Name: products products_brandid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_brandid_fkey FOREIGN KEY (brandid) REFERENCES public.brands(brandid);


--
-- Name: products products_categoryid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_categoryid_fkey FOREIGN KEY (categoryid) REFERENCES public.categories(categoryid);


--
-- PostgreSQL database dump complete
--

