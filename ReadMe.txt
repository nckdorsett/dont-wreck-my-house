MODELS:

Guest:
Fields, getters/setters, Equals/Hashcode, general toString
Host:
Fields, getters/setters, Equals/Hashcode, general toString
Reservation:
Fields, getters/setters, Equals/Hashcode, general toString, calcTotal




DATA:

General DataException to be used when data is not writeable.

GuestRepository/GuestFileRepository:
findAll, findById, findByEmail
add, update, delete
serialize, deserialize, writeAll
tests completed for all methods.

HostRepository/HostFileRepository:
findAll, findById, findByEmail
add, update, delete
serialize, deserialize, writeAll
tests completed for all methods.

ReservationRepository/ReservationFileRepository:
uses directory instead of specific file.
findByHost
add, update, delete
serialize, deserialize, getFilePath, writeAll, getDate
tests completed for all methods.




DOMAIN:


