drop database restaurant;
create database restaurant;
use restaurant;

create table dish(
	name varchar(100) not null,
	minutes int(5) not null,
	instructions text,
	category enum('soup','appetizer','salad','main'),
	main enum('pasta','pasta','gnocchi','pizza'),
	price dec(6,2) not null,
	primary key(name)
)Engine=InnoDB;

create table ingredients(
	name varchar(100) not null,
	availability dec(6,2) not null,
	unit varchar(100) default '',
	minimum dec(6,2) not null,
	price dec(6,2),
	primary key(name)
)Engine=InnoDB;

create table materials(
	recipe varchar(100) not null,
	material varchar(100) not null,
	quantity dec(6,2) not null,
	unit varchar(100) default '',
	primary key(recipe,material),
	foreign key(recipe) references dish(name)
	on update cascade on delete cascade,
	foreign key(material) references ingredients(name)
	on update cascade on delete cascade
)Engine=InnoDB;

create table dish_of_the_day(
	name varchar(100) not null,
	availability int(5) not null,
	soup varchar(100) not null,
	appetizer varchar(100) not null,
	main varchar(100) not null,
	primary key(name),
	foreign key(soup) references dish(name)
	on update cascade on delete cascade,
	foreign key(appetizer) references dish(name)
	on update cascade on delete cascade,
	foreign key(main) references dish(name)
	on update cascade on delete cascade
)Engine=InnoDB;

create table drink(
	name varchar(100) not null,
	type enum('water','refreshment','juice','wine','beer'),
	availability int(5),
	price dec(6,2) not null,
	primary key(name)
)Engine=InnoDB;

create table employee(
	id int(5) not null auto_increment,
	full_name varchar(100) not null,
	profession
	enum('waiter','chef','cleaner','cashier',
		'tel_operator','server','shift manager'),
	cost dec(6,2) not null,
	degree text,
	cv text,
	experience int(5),
	hired date,
	primary key(id)
)Engine=InnoDB;				

create table work(
	waiter1 int(5) not null,
	waiter2 int(5) not null,
	chef1 int(5) not null,
	chef2 int(5) not null,
	chef3 int(5) not null,
	cleaner1 int(5) not null,
	cleaner2 int(5) not null,
	cashier int(5) not null,
	tel_operator int(5) not null,
	server1 int(5) not null,
	server2 int(5) not null,
	shift_manager int(5) not null,
	date_stamp date not null,
	shift enum('day','night'),
	primary key(date_stamp,shift),
	foreign key(waiter1) references employee(id)
	on update cascade on delete cascade,
	foreign key(waiter2) references employee(id)
	on update cascade on delete cascade,
	foreign key(chef1) references employee(id)
	on update cascade on delete cascade,
	foreign key(chef2) references employee(id)
	on update cascade on delete cascade,
	foreign key(chef3) references employee(id)
	on update cascade on delete cascade,
	foreign key(cleaner1) references employee(id)
	on update cascade on delete cascade,
	foreign key(cleaner2) references employee(id)
	on update cascade on delete cascade,
	foreign key(cashier) references employee(id)
	on update cascade on delete cascade,
	foreign key(tel_operator) references employee(id)
	on update cascade on delete cascade,
	foreign key(server1) references employee(id)
	on update cascade on delete cascade,
	foreign key(server2) references employee(id)
	on update cascade on delete cascade,
	foreign key(shift_manager) references employee(id)
	on update cascade on delete cascade
	
)Engine=InnoDB;


create table tables(
	table_number int(5) not null auto_increment,
	capacity enum('4','8','12'),
	description enum('window','patio','garden','hall'),
	primary key(table_number)
)Engine=InnoDB;


create table waiter(
	date_stamp date not null,
	shift enum('day','night'),
	id int(5) not null,
	table_num int(5) not null,
	primary key(date_stamp,shift,id,table_num),
	foreign key(id) references employee(id)
	on update cascade on delete cascade,
	foreign key(table_num) references tables(table_number)
	on update cascade on delete cascade,
	foreign key(date_stamp,shift) references work(date_stamp,shift)
	on update cascade on delete cascade
)Engine=InnoDB;


create table customer(
	id int(5) not null auto_increment,
	full_name varchar(100),
	bonus int(5) default 0,
	discount int(5) default 0,
	primary key(id)
)Engine=InnoDB;


create table coupon(
	coupon_id int(5) not null auto_increment,
	customer_id int(5) not null,
	date_from date not null,
	date_to date not null,
	used varchar(100) default 'no',
	primary key(coupon_id),
	foreign key(customer_id) references customer(id)
	on update cascade on delete cascade
)Engine=InnoDB;


create table orders(
	num int(10) not null auto_increment,
	date_stamp datetime not null,
	shift enum('day','night'),
	chef int(5) not null,
	waiter int(5) not null,
	table_num int(5) not null,
	customer_id int(5) not null,
	coupon int(5) not null,
	card enum('no','yes'),
	discount int(5),
	primary key(num),
	foreign key(chef) references employee(id)
	on update cascade on delete cascade,
	foreign key(waiter) references employee(id)
	on update cascade on delete cascade,
	foreign key(table_num) references tables(table_number)
	on update cascade on delete cascade,
	foreign key(customer_id) references customer(id)
	on update cascade on delete cascade
)Engine=InnoDB;

create table order_dishes(
	num int(10) not null,
	food int(5) not null,
	name varchar(100) not null,
	pasta varchar(100),
	pizza enum('4','8','16'),
	primary key(num,food),
	foreign key(name) references dish(name)
	on update cascade on delete cascade,
	foreign key(pasta) references ingredients(name)
	on update cascade on delete cascade
)Engine=InnoDB;

create table extra_ingredients(
	id int(5) not null auto_increment,
	ingredient varchar(100) not null,
	num int(10) not null,
	food int(5) not null,
	primary key(id),
	foreign key(ingredient) references ingredients(name)
	on update cascade on delete cascade
)Engine=InnoDB;

create table without_ingredients(
	id int(5) not null auto_increment,
	ingredient varchar(100) not null,
	num int(10) not null,
	food int(5) not null,
	primary key(id),
	foreign key(ingredient) references ingredients(name)
	on update cascade on delete cascade
)Engine=InnoDB;

create table order_dish_of_the_day(
	order_id int(10) not null,
	num int(5) not null,
	name varchar(100) not null,
	primary key(order_id,name,num),
	foreign key(name) references dish_of_the_day(name)
	on update cascade on delete cascade
)Engine=InnoDB;


create table order_drinks(
	order_id int(10) not null,
	num int(5) not null,
	name varchar(100) not null,
	primary key(order_id,num,name),
	foreign key(name) references drink(name)
	on update cascade on delete cascade
)Engine=InnoDB;
