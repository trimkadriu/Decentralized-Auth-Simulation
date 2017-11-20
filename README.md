# Decentralized Authentication Simulation by using Reputation over Blockchain

## Description
A simulation software created for the masters thesis with the title of 

**"Introducing a Decentralized Framework to Provide Authentication on IoT devices"**

This application is an implementation of the proposed framework on the dissertation.
The application can run as three different roles and with predefined parameters. Besides that, it contains a property file
to change the configurations of the framework as you want. Each role can make different interactions with transactions.
The intention is to simulate the blockchain by using a central database which follows a distributed reputation scheme.

Run the application to see all the parameters needed to pass.

## Features
Some of the features of this application are:

- Blockchain simulation by using a central DB
- Asynchronized transactions on Blockchain
- Public Key cryptography implementation
- Verifying transactions by Digital Signatures
- Calculating Proof-of-Work by using Leading Zeros
- Distributed Reputation Scheme

## Requirements
This application was developed and tested with the following specifications:

- Java JDK 8
- MySQL 5.6.26
- Maven 4
- Apache Commons

## Installation
To install this application first you need to make your environment ready as specified in requirements section.
Then clone this repository and run the following on command line:

- `git clone [repo_url]`
- `mvn install`
- `mysql -u [username] -p [database_name] < dec_auth_sim.sql`
- `Change the configuration file accordingly ("src/main/resources/config.properties")`

## TODO: Improvements in the future
- Add Spring to use the Dependency Injection
- Add Hibernate for easier and better SQL Queries
- Add AutoCreate Schema if does not exists functionality

## Author
This project is developed as part of the Dissertation at The University of Sheffield - International Faculty, CITY College.

**Student:** Trim Kadriu
