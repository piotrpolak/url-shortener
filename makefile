dockerfullrun:
	docker compose rm --stop -f && docker compose up --build --force-recreate

dockerfullprodbuild:
	mvn clean verify && docker build .

dockerprodbuild:
	docker build .
