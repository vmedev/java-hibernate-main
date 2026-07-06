# Practical Task: Library Book Tracker API

Vladlens Medvedevs

## Behavioural Requirements Completed
- Books can be created
- All books can be retrieved
- A single book can be retrieved by ID
- Books can be deleted
- Books can be filtered and searched by their fields.
- Controller delegates to Service, which delegates to Repository
- DTOs are used for requests and responses

## Stretch goals Completed
- Request validation
- Custom exception handling
- Searching by partial title
- Add 'borrow' and 'return' endpoints along with the new 'borrowedStatus' field to the Book entity 
(kept the existing 'available' field instead of adding 'borrowedStatus', since both would carry the same meaning)