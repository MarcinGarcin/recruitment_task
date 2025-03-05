This is a recruitment task for Atipera, involving the development of an API designed to retrieve and provide structured data about a user's public repositories. The API should efficiently fetch repository details, ensuring accuracy and clarity while maintaining optimal performance.

## Technologies

The project is built using the following technologies and tools:

  - **quarkus-rest**: Provides support for developing RESTful APIs using JAX-RS..
  - **quarkus-arc**: Enables dependency injection using CDI (Contexts and Dependency Injection.
  - **quarkus-rest-client-jackson**: Provides a REST client for making HTTP requests.

## Installation

To run the project locally, follow these steps:

1. Clone the repository:  
   ```bash
   git clone https://github.com/MarcinGarcin/recruitment_task.git
   cd recruitment_task
2. Build the project using Maven:
   ```bash
   mvn clean install
3. Run the application:
   ```bash
   mvn quarkus:dev
4. The backend API will be available at
   ```bash
   http://localhost:8080/github/repos/
