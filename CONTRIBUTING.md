# EasyDao

EasyDao is a lightweight, fast and flexible model and dao code generator.

## Development environment

Install docker, Java8.

On MacOS: export JAVA_HOME=`/usr/libexec/java_home -v 1.8`

1. Clone the repo.
2. Start a postgresql database: more information in https://github.com/vanioinformatika/easydao/blob/master/easydao-samples/README.md
3. Run easydao project.

## Testing

`mvn clean verify`

## Releasing

`git pull`
`mvn release:prepare`
`mvn release:perform`
`git push --tags`
