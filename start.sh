#!/usr/bin/env sh

set -e

TARGET_DIR=${TARGET_DIR:-"/app/target"}
DB_URL=${DB_URL:-"jdbc:postgresql://db:5432/impactoambiental"}
DB_USERNAME=${DB_USERNAME:-"jpademo"}
DB_PASSWORD=${DB_PASSWORD:-"jpademo"}

sed -i "s,name=\"hibernate\.connection\.url\" value=\".*\",name=\"hibernate\.connection\.url\" value=\"$DB_URL\",g" "$TARGET_DIR/classes/META-INF/persistence.xml"
sed -i "s,name=\"hibernate\.connection\.username\" value=\".*\",name=\"hibernate\.connection\.username\" value=\"$DB_USERNAME\",g" "$TARGET_DIR/classes/META-INF/persistence.xml"
sed -i "s,name=\"hibernate\.connection\.password\" value=\".*\",name=\"hibernate\.connection\.password\" value=\"$DB_PASSWORD\",g" "$TARGET_DIR/classes/META-INF/persistence.xml"

grep "hibernate.connection.url" "$TARGET_DIR/classes/META-INF/persistence.xml"

sh -c "$@"
