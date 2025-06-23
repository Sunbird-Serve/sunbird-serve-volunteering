# Sunbird Serve Volunteering

A Spring Boot application for managing volunteer services and user profiles.

## 🚀 Features

- User Management (CRUD operations)
- Agency Management
- Volunteer Hours Tracking
- Email Notifications
- RESTful API with Swagger Documentation

## 🛠 Technology Stack

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring WebFlux** (Reactive programming)
- **PostgreSQL** (Database)
- **Gradle** (Build tool)
- **Docker** (Containerization)
- **Swagger/OpenAPI** (API Documentation)

## 📋 Prerequisites

- Java 17 or higher
- PostgreSQL 12 or higher
- Gradle 7.6 or higher
- Docker (optional)

## 🔧 Environment Setup

### Option 1: Using .env file (Recommended for local development)

1. **Copy the example environment file:**
```bash
cp env.example .env
```

2. **Edit the `.env` file** with your actual values:
```bash
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/ev-vriddhi
DB_USERNAME=postgres
DB_PASSWORD=your_secure_password

# Email Configuration
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# External Service Configuration
RC_SERVICE_URL=http://localhost:8081/api/v1/
```

### Option 2: Using Environment Variables

Set the following environment variables:

#### 1. Database Configuration
```bash
export DB_URL=jdbc:postgresql://localhost:5432/ev-vriddhi
export DB_USERNAME=postgres
export DB_PASSWORD=your_secure_password
```

#### 2. Email Configuration
```bash
export MAIL_HOST=smtp.gmail.com
export MAIL_PORT=587
export MAIL_USERNAME=your_email@gmail.com
export MAIL_PASSWORD=your_app_password
```

#### 3. External Service Configuration
```bash
export RC_SERVICE_URL=http://your-service-url:8081/api/v1/
```

#### 4. Optional Configuration
```bash
export JPA_SHOW_SQL=false
export INCLUDE_EXCEPTION=false
export INCLUDE_STACKTRACE=never
```

### Environment Variables Reference

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `DB_URL` | PostgreSQL connection URL | `jdbc:postgresql://localhost:5432/ev-vriddhi` | Yes |
| `DB_USERNAME` | Database username | `postgres` | Yes |
| `DB_PASSWORD` | Database password | - | Yes |
| `MAIL_HOST` | SMTP server host | `smtp.gmail.com` | Yes |
| `MAIL_PORT` | SMTP server port | `587` | Yes |
| `MAIL_USERNAME` | Email username | - | Yes |
| `MAIL_PASSWORD` | Email password/app password | - | Yes |
| `RC_SERVICE_URL` | External service base URL | `http://localhost:8081/api/v1/` | Yes |
| `JPA_SHOW_SQL` | Enable SQL logging | `false` | No |
| `INCLUDE_EXCEPTION` | Include exception in error responses | `false` | No |
| `INCLUDE_STACKTRACE` | Include stack trace in error responses | `never` | No |

## 🏃‍♂️ Running the Application

### Using Gradle

```bash
# Build the application
./gradlew build

# Run the application
./gradlew bootRun
```

### Using Docker

```bash
# Build Docker image
docker build -t sunbird-serve-volunteering .

# Option 1: Run with .env file (Recommended)
docker run -p 9090:9090 \
  --env-file .env \
  sunbird-serve-volunteering

# Option 2: Run with individual environment variables
docker run -p 9090:9090 \
  -e DB_URL=jdbc:postgresql://host.docker.internal:5432/ev-vriddhi \
  -e DB_USERNAME=postgres \
  -e DB_PASSWORD=your_password \
  -e MAIL_HOST=smtp.gmail.com \
  -e MAIL_USERNAME=your_email@gmail.com \
  -e MAIL_PASSWORD=your_app_password \
  -e RC_SERVICE_URL=http://host.docker.internal:8081/api/v1/ \
  sunbird-serve-volunteering
```

## 🧪 Testing

### Run All Tests

```bash
./gradlew test
```

### Run Specific Test Categories

```bash
# Unit tests only
./gradlew test --tests "*Test"

# Integration tests only
./gradlew test --tests "*IntegrationTest"

# Controller tests only
./gradlew test --tests "*ControllerTest"
```

### Test Coverage

```bash
./gradlew test jacocoTestReport
```

## 📚 API Documentation

Once the application is running, you can access:

- **Swagger UI**: http://localhost:9090/api/v1/serve-volunteering/swagger-ui.html
- **API Base URL**: http://localhost:9090/api/v1/serve-volunteering/

### Main Endpoints

- **User Management**: `/user/*`
- **Agency Management**: `/agency/*`
- **Volunteer Hours**: `/volunteer-hours/*`

## 🔒 Security Features

### Input Validation
- All request models are validated using Bean Validation annotations
- Email format validation
- Phone number format validation
- Required field validation

### Error Handling
- Global exception handler for consistent error responses
- Proper HTTP status codes
- Detailed error messages for debugging
- Security-conscious error responses (no sensitive data exposure)

### Configuration Security
- Environment variable-based configuration
- No hardcoded credentials
- Secure defaults for production

## 🚨 Security Best Practices

1. **Never commit credentials** to version control
2. **Use environment variables** for all sensitive configuration
3. **Enable HTTPS** in production
4. **Implement proper authentication** and authorization
5. **Regular security updates** for dependencies
6. **Input validation** on all endpoints
7. **Proper error handling** without information leakage

## 📊 Monitoring and Logging

### Logging Configuration
- Structured logging with SLF4J
- Configurable log levels
- Separate log files for different environments

### Health Checks
- Application health endpoint: `/actuator/health`
- Database connectivity checks
- External service health monitoring

## 🚀 Deployment

### Production Deployment Checklist

- [ ] Set all required environment variables
- [ ] Configure production database
- [ ] Set up email service
- [ ] Configure external service URLs
- [ ] Enable HTTPS
- [ ] Set up monitoring and alerting
- [ ] Configure backup strategies
- [ ] Set up CI/CD pipeline

### Docker Deployment

```bash
# Production Docker run
docker run -d \
  --name sunbird-volunteering \
  -p 9090:9090 \
  --env-file .env.production \
  sunbird-serve-volunteering:latest
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Submit a pull request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the API documentation

## 🔄 Version History

- **v0.0.1-SNAPSHOT**: Initial release with basic functionality