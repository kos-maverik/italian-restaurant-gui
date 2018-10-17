delimiter $

/* Functions */

/* Returns price including the discount 
	and the extra/less ingredients */

drop function if exists price$
create function price(ord int)
returns float

begin

	declare cost float;
	declare total float;

	declare ord_name varchar(100);
	declare pasta varchar(100);
	declare size enum('4','8','16');

	declare finished int;
	declare ordCursor cursor for
	select name, pasta, pizza
	from order_dishes
	where num=ord;
	declare continue handler for not found set finished=1;
	
	set cost = 0;
	set total = 0;

	open ordCursor;
	set finished=0;
	fetch ordCursor into ord_name, pasta, size;
	while (finished=0) do
		if ((select main from dish
			where name=ord_name)='pasta') then
				if pasta is not null then
					select ingredients.price into cost
					from ingredients
					where name=pasta;
					set total = total + cost;

					select ingredients.price into cost
					from ingredients
					where name = (
					select material from materials
					where recipe=ord_name
					limit 1 );
					set total = total - cost;
				end if;
		elseif ((select main from dish
			where name=ord_name)='pizza') then
			if size = '8' then
				select price *0.5 into cost
				from dish
				where name=ord_name;

				set total = total + cost;
			elseif size= '16' then
				select price into cost
				from dish
				where name=ord_name;

				set total = total + cost;
			end if;
		end if;
		
		fetch ordCursor into ord_name, pasta, size;
	end while;
	close ordCursor;


	select coalesce(sum(dish.price), 0) into cost
	from order_dishes
	inner join dish
	on order_dishes.name=dish.name
	where order_dishes.num=ord;

	set total = total+cost;

	select coalesce(sum(ingredients.price), 0) into cost
	from extra_ingredients
	inner join ingredients
	on extra_ingredients.ingredient=ingredients.name
	where extra_ingredients.num=ord;

	set total = total+cost;

	select coalesce(sum(ingredients.price), 0) into cost
	from without_ingredients
	inner join ingredients
	on without_ingredients.ingredient=ingredients.name
	where without_ingredients.num=ord;

	set total = total-cost;

	select coalesce(sum(drink.price), 0) into cost
	from order_drinks
	inner join drink
	on order_drinks.name=drink.name
	where order_drinks.order_id=ord;

	set total = total+cost;
	
	select coalesce(sum(dish.price), 0) into cost
	from order_dish_of_the_day
	inner join dish_of_the_day
	on order_dish_of_the_day.name=dish_of_the_day.name
	inner join dish
	on dish_of_the_day.soup=dish.name
	or dish_of_the_day.appetizer=dish.name
	or dish_of_the_day.main=dish.name
	where order_dish_of_the_day.order_id=ord;

	set total = total+cost;

	select discount into cost
	from orders
	where orders.num=ord;

	set total = total-cost;

	select orders.coupon*10 into cost
	from orders
	where orders.num=ord;

	set total = total-cost;

	if (total < 0) then
		set total = 0;
	end if;

	return total;

end; $


/* Returns number of dishes in an orders
	for every dish of the day adds +3 */

drop function if exists dishes$
create function dishes(ord int)
returns int

begin

	declare temp float;
	declare total float;

	set temp = 0;
	set total = 0;

	select count(1) into temp
	from orders
	inner join order_dishes
	on orders.num=order_dishes.num
	where order_dishes.num=ord;

	set total = total+temp;

	select count(1)*3 into temp
	from orders
	inner join order_dish_of_the_day
	on orders.num=order_dish_of_the_day.order_id
	where orders.num=ord;

	set total = total+temp;

	return total;

end; $


/* Queries */

drop procedure if exists query1$
create procedure query1(in date_from date, in date_to date)
begin

	select id, full_name, profession,
	count(1)*10 as 'Working hours', count(1)*10*cost as 'Cost'
	from employee
	inner join work on id=waiter1
	or waiter2=id
	or chef1=id
	or chef2=id
	or chef3=id
	or cleaner1=id
	or cleaner2=id
	or cashier=id
	or tel_operator=id
	or server1=id
	or server2=id
	or shift_manager=id
	where date_stamp between '2015-09-01' and '2015-09-02'
	group by id;

end$


drop procedure if exists query2$
create procedure query2()
begin

	select 'New ingredients are needed:' as Message;

	select name as Sustatiko from ingredients
	where availability<minimum;
end$


drop procedure if exists query3$
create procedure query3()
begin

	select 'These dishes are not available:' as Message;

	select recipe, material, quantity, availability from materials
	inner join ingredients
	on materials.material=ingredients.name
	where materials.quantity>ingredients.availability;

end$


drop procedure if exists query4$
create procedure query4(in day date)
begin

	select 'Available tables:' as Message;

	select tables.table_number as 'Table',
	tables.description as 'Location',
	employee.full_name as 'Waiter',
	tables.capacity as 'Capacity'
	from tables
	inner join waiter
	on tables.table_number=waiter.table_num
	inner join employee
	on waiter.id=employee.id
	where (tables.capacity='8'
	or tables.capacity='12')
	and waiter.date_stamp = day
	and waiter.shift = 'night';

end$


drop procedure if exists query5$
create procedure query5(in date_from date, in date_to date)
begin

	select 'Most sold dish:' as Message;
	if (isnull(date_from) or isnull(date_to)) then
		select day as 'Date', name as 'Dish', num as 'Number' from (
			select date(orders.date_stamp) as day, name, count(1) as num
			from order_dishes
			inner join orders
			on order_dishes.num=orders.num
			group by date(orders.date_stamp), name
			order by count(1) desc, date_stamp, name
		) as t1
		group by t1.day;
	else
		select date_from as 'From', date_to as 'To', name as 'Dish', count(1) as 'Number'
		from order_dishes
		inner join orders
		on order_dishes.num=orders.num
		where date(date_stamp) between date_from and date_to
		group by name
		order by count(1) desc, date_stamp, name
		limit 1;
	end if;

end$


drop procedure if exists query6$
create procedure query6()
begin

	declare finished int;
	declare cust_id int;
	declare name varchar(100);
	declare min_order int;
	declare max_order int;
	declare min_cost float;
	declare max_cost float;

	declare customer cursor for
	select id,full_name from customer;
	declare continue handler for not found set finished=1;

	open customer;
	set finished=0;
	fetch customer into cust_id, name;
	while (finished=0) do
		select orders.num,
		price(orders.num) as price into min_order,min_cost
		from customer
		inner join orders
		on customer.id=orders.customer_id
		where customer.id=cust_id
		order by price asc
		limit 1;

		select orders.num,
		price(orders.num) as price into max_order,max_cost
		from customer
		inner join orders
		on customer.id=orders.customer_id
		where customer.id=cust_id
		order by price desc
		limit 1;

		select name as 'Name', min_cost as 'Min', min_order as 'orders',
		max_cost as 'Max', max_order as 'orders';

		fetch customer into cust_id, name;
	end while;
	close customer;

end$


drop procedure if exists query7$
create procedure query7(in date_from date, in date_to date)
begin

	select coalesce(sum(price(num)), 0)/count(1) as 'Average orders cost'
	from orders
	where date(date_stamp) between date_from and date_to;
	
end$


drop procedure if exists query8$
create procedure query8(in day date, in shift enum('day','night'))
begin

	select employee.full_name as 'Chef',
	sum(dishes(orders.num)) as 'Dishes'
	from orders
	inner join employee
	on orders.chef=employee.id
	where date(orders.date_stamp)=day
	and orders.shift=shift
	group by orders.chef;

end$


drop procedure if exists query9$
create procedure query9(in waiter int, in day date, in shift enum('day','night'))
begin

	select employee.full_name as 'Waiter',
	count(1) as 'Orders'
	from orders
	inner join employee
	on orders.waiter=employee.id
	where date(orders.date_stamp)=day
	and orders.shift=shift
	and employee.id=waiter
	group by orders.waiter;

end$


drop procedure if exists query10$
create procedure query10()
begin

	select customer.full_name as 'Customer',
	coupon.date_from as 'From',
	coupon.date_to as 'To'
	from coupon
	inner join customer
	on coupon.customer_id=customer.id;

end$


/* Restaurant revenues for a given period */
drop procedure if exists bonus1$
create procedure bonus1(in date_from date, in date_to date)
begin

	select coalesce(sum(price(num)), 0) as 'Income'
	from orders
	where date(date_stamp) between date_from and date_to;

end$


/* Most profitable table for a given period */
drop procedure if exists bonus2$
create procedure bonus2(in date_from date, in date_to date)
begin

	select f1 as 'Table', f2 as 'Profit'
	from (
		select table_num as f1,
		coalesce(sum(price(num)), 0) as f2
		from orders
		where date(date_stamp) between date_from and date_to
		group by table_num
	) as t1
	order by f2 desc
	limit 1;

end$



/* Most sold drink per day or for a given period */
drop procedure if exists bonus3$
create procedure bonus3(in date_from date, in date_to date)
begin
	select 'Most sold drink:' as Message;
	if (isnull(date_from) or isnull(date_to)) then
		select day as 'Date', name as 'Drink', num as 'Number' from (
			select date(orders.date_stamp) as day, name, count(1) as num
			from order_drinks
			inner join orders
			on order_drinks.order_id=orders.num
			group by date(orders.date_stamp), name
			order by count(1) desc, date_stamp, name
		) as t1
		group by t1.day;
	else
		select date_from as 'From', date_to as 'To', name as 'Drink', count(1) as 'Number'
		from order_drinks
		inner join orders
		on order_drinks.order_id=orders.num
		where date(date_stamp) between date_from and date_to
		group by name
		order by count(1) desc, date_stamp, name
		limit 1;
	end if;

end$


/* Most profitable customer for a given period */
drop procedure if exists bonus4$
create procedure bonus4(in date_from date, in date_to date)
begin

	select f1 as 'Customer', f2 as 'Sum'
	from (
		select customer.full_name as f1,
		coalesce(sum(price(num)), 0) as f2
		from orders
		inner join customer
		on orders.customer_id=customer.id
		where date(date_stamp) between date_from and date_to
		group by customer_id
	) as t1
	order by f2 desc
	limit 1;

end$


/* Average cost of all dishes */
drop procedure if exists bonus5$
create procedure bonus5()
begin

	select coalesce(sum(price), 0)/count(1) as 'Average dish cost'
	from dish;

end$


/* Triggers */

/* Checks if the customer has the discount
	coupons that the orders suggests */

drop trigger if exists checkCoupon$
create trigger checkCoupon before insert on orders
for each row
begin

	declare coupon int;
	declare sum int;
	declare num int;
	declare discount int;


	set num = new.coupon;
	select count(1) into sum from coupon
	where coupon.customer_id=new.customer_id
	and used='no';

	/* They have the coupons */
	if sum >= num then
		while (num > 0) do
			select coupon.coupon_id into coupon
			from coupon
			where coupon.customer_id=new.customer_id
			and used='no'
			limit 1;

			set num = num-1;

			update coupon set used='yes'
			where coupon_id=coupon;
		end while;
	else
	/* They do not have (enough) coupons */
		set new.coupon = sum;
		update coupon set used='yes'
		where customer_id=new.customer_id;
	end if;

	/* Checks if the orders has
		20 euro discount bonus */
	select discount into discount
	from customer
	where id=new.customer_id;

	if discount>0 then
		set new.discount = discount;
	end if;

end$


/* In orders to prevent negative availability */
drop trigger if exists checkDrinks$
create trigger checkDrinks before insert on order_drinks
for each row
begin
	declare numb int;
	select availability into numb from drink
	where drink.name=new.name;

	if numb>0 then
		update drink set availability = availability -1
		where name=new.name;
	end if;
end$


/* In orders to prevent negative availability */

drop trigger if exists checkSpecial$
create trigger checkSpecial before insert on order_dish_of_the_day
for each row
begin
	declare numb int;
	select availability into numb from dish_of_the_day
	where dish_of_the_day.name=new.name;

	if numb>0 then
		update dish_of_the_day set availability = availability -1
		where name=new.name;
	end if;
end$


/* Checks if the customer can get a 10 euro discount coupon
	and adds the bonus points to the customer's card */
drop trigger if exists bonusPoints$
create trigger bonusPoints after insert on orders
for each row
begin

	declare last_coupon date;
	declare num_orders int;
	declare points float;

	if exists (
		select 1 from coupon
		where coupon.customer_id=new.customer_id
		) then
			/* If they have other coupons */
			select coupon.date_to into last_coupon
			from coupon
			where coupon.customer_id=new.customer_id
			order by coupon.date_to desc
			limit 1;

			select count(1) into num_orders
			from orders
			where orders.customer_id=new.customer_id
			and date(orders.date_stamp)>last_coupon
			and orders.date_stamp >= new.date_stamp - interval 1 month;

			if num_orders >= 10 then
				if (new.date_stamp - interval 1 month > last_coupon) then
					/* 10 orders have been placed in less than
						a month after the last coupon was used */
					insert into coupon values
					(null,new.customer_id,
					date(new.date_stamp)-interval 1 month,date(new.date_stamp),'no');
				else
					insert into coupon values
					(null,new.customer_id,
					last_coupon+interval 1 day,date(new.date_stamp),'no');
				end if;
			end if;

	else
			/* Customer's first discount coupon */
			select count(1) into num_orders
			from orders
			where orders.customer_id=new.customer_id
			and orders.date_stamp >= new.date_stamp - interval 1 month;

			if num_orders >= 10 then
				insert into coupon values
				(null,new.customer_id,
				date(new.date_stamp)-interval 1 month,date(new.date_stamp),'no');
			end if;
	end if;

	/* If they brought their discount card */
	if new.card = 'yes' then
		select customer.bonus into points
		from customer
		where customer.id=new.customer_id
		limit 1;

		set points = points + price(new.num)/10;

		if points >= 200 then
			/* 20 euro discount in the next orders! */
			update customer set bonus=points-200, discount=20
			where id=new.customer_id;
		else
			/* +10% of the orders in bonus points */
			update customer set bonus=points, discount=0
			where id=new.customer_id;
		end if;
	end if;

end$


/* Checks if the ingredients of a dish are present
	and if yes, they get removed */

drop trigger if exists checkIngredients$
create trigger checkIngredients before insert on order_dishes
for each row
begin

	declare ingr varchar(100);
	declare quant float;

	declare finished int;
	declare ingCursor cursor for
	select material, quantity from materials
	where recipe=new.name;
	declare continue handler for not found set finished=1;

	if exists (select 1 from ingredients
	inner join materials
	on ingredients.name=materials.material
	inner join order_dishes
	on materials.recipe=order_dishes.name
	where materials.quantity>ingredients.availability
	and order_dishes.name=new.name ) then
			/* The dish cannot be made */
			set new.food=null;
	else
			/* Remove the ingredients used */
			open ingCursor;
			set finished=0;
			fetch ingCursor into ingr, quant;

			while (finished=0) do
				update ingredients
				set availability=availability - quant
				where name=ingr;

				fetch ingCursor into ingr, quant;
			end while;
			close ingCursor;
	
	end if;
	

end$


delimiter ;
