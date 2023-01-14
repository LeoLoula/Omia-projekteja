const mongoose = require('mongoose')

const password = process.argv[2]

const url =
  'mongodb+srv://<user>:' +
  password +
  '@cluster0.rbz33.mongodb.net/phonebookapp?retryWrites=true&w=majority'

mongoose.connect(url)

const personSchema = new mongoose.Schema({
  name: String,
  number: String,
})

const Person = mongoose.model('Person', personSchema)

const person = new Person({
  name: process.argv[3],
  number: process.argv[4],
  id: 5,
})

if (
  process.argv[3] === undefined ||
  process.argv[3] === 'Person' ||
  process.argv[3] === 'person'
) {
  Person.find({}).then((persons) => {
    console.log('Puhelinluettelo:')
    persons.forEach((person) => {
      console.log(person.name + ' ' + person.number)
    })
    mongoose.connection.close()
  })
} else {
  console.log(
    `adding person ${process.argv[3]} number ${process.argv[4]} to the directory: `
  )
  console.log(url)
  person.save().then((result) => {
    mongoose.connection.close()
  })
}
