# Pizzeria

## Objectives

* Create an implementation for a simple Pizzeria system
* **Features to implement**:
    * <i>Creating an order</i>
    * <i>Creating an order with a discount - at Tuesdays 10% discounts for 10y/o kids and at
      Thursdays 40% discounts for students</i>
    * <i>Option for eating on-site or take-away</i>
    * <i>If there is no available cook to pick up an order, the order should be put to the
      queue</i>
    * <i>If there is any order in a queue the cooks should automatically pick up order from the
      queue</i>
    * <i>If there is no available table for a customer order should be canceled</i>
    * <i>Pizzeria should keep stock of ingredients and if amount of any ingredient drop below
      minimum Pizzeria should make an order for this ingredient</i>
* Write tests for your implementation

[//]: # (sudo docker run -d -name pizzera -p 1521:1521 -p 5500:5500 -e ORACLE_PASSWORD=admin -v /home/user/oracle/oradata gvenal/oracle-free)


[//]: # (to run on dev: mvn spring-boot:run -Dspring-boot.run.profiles=dev)

[//]: # (to run on prod: mvn spring-boot:run -Dspring-boot.run.profiles=prod)
