GitHub Repo Info
GitHub Repo Info is a RESTful web application that allows users to fetch information about a GitHub user's repositories. You can find repositories that are not forks and learn about their branches and latest commits.

Requirements
To interact with this application, you'll need:

Java: This is a Java-based application, so ensure you have Java installed on your computer.
Maven: Maven is used for managing dependencies and building the project.

Running the Application
1.Clone the repository: Clone this repository on your computer.
git clone https://github.com/YOUR-USERNAME/GitHub-Repo-Info.git

2.Navigate to the application directory: Go to the directory created after cloning the repository.
cd GitHub-Repo-Info

3.Run the application: Launch the application using the following command:
mvn spring-boot:run

Using the Application
GitHub Repo Info is designed to be used programmatically using HTTP requests, not through a web browser. You can interact with the application using tools like Postman or by making HTTP requests programmatically in your code.

Here's how to use the application:

Make an HTTP GET request to http://localhost:8080/api/github/repositories/{username}, where {username} is the GitHub username you want to query.

Set the Accept header in your request to specify the desired response format. Available formats are "application/json".

The application will respond with a JSON representation of the user's non-fork repositories, along with information about branches and the latest commits.
