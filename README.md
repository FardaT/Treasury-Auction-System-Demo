# Treasury-Auction-System

Learning project originally created by a collaboration of 5 junior developers over a period of 6-weeks to get accommodated with working as a team on a single Spring Boot project.
Since then further enhancements have been made to showcase and deploy the project as a demonstration.

The application serves as a pseudo Dutch auction system modelled after the auction process of the U.S. Treasury.
The business logic follows the specifications of this system's specifications.

To learn more about the auctioning of U.S. securities, please check the following pages:
https://www.investopedia.com/terms/d/dutchauction.asp
https://www.treasurydirect.gov/auctions/

## Features

### Registration & Loggin in
- The application follows a standard registration journey with email verification as well as admin approval process
- Authentication & authorization is provided by Spring Security
### Auction & Bidding
- Once logged in, users may bid on ongoing auctions with competitive and non-competitive bids up until the auction expires
- Validations for business logic (bid limitations) are both handled client and server side
- Users may also see their bids on their profile page

### Creating a new auction
- Admins may create new auctions (they may not participate)
- After setting a number of securities to be included for the auction, Bidding Bot may also bet set for demonstration purposes
- Bots can place bids on certain securities within an auction, their behaviour is randomized based on whether they are retail or institutional investors

### Processing bids
- At the end of each auction, bids are processed according to the dutch auction logic and bidders are awarded with securities accordingly
### Rest API
- The application features a number of open endpoints which serve requests to retrieve information on auctions

## Technologies
- Java 17, Spring Boot, Gradle, Hibernate, MySQL, HTML, CSS, JavaScript, JUnit, Mockito
- Tools: IntelliJ IDEA, JIRA, Github, Github Actions, Heroku

### Credits
Contributors: redflesher, dlukacs01, szecsiistvan, FardaT, NathalieBazinga
