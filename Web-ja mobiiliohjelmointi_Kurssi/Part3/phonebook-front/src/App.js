import './App.css'
import React, { useState, useEffect } from 'react'
import axios from 'axios'
const baseUrl = 'https://phonebook-backend00.herokuapp.com/api/persons'

const App = () => {
  const [persons, setPersons] = useState([])
  const [newName, setNewName] = useState('')
  const [newNumber, setNewNumber] = useState('')

  useEffect(() => {
    axios.get(baseUrl).then((response) => {
      setPersons(response.data)
    })
  }, [])

  const deleteperson = async (id, name) => {
    if (window.confirm('Poistetaanko ' + name)) {
      await axios.delete(
        `https://phonebook-backend00.herokuapp.com/api/persons/${id}`
      )
      setPersons(persons.filter((person) => person.id !== id))
      console.log(persons)
    }
  }

  const handleNewname = (event) => {
    setNewName(event.target.value)
  }

  const handleNewnumber = (event) => {
    setNewNumber(event.target.value)
  }

  const addNew = (event) => {
    event.preventDefault()
    console.log('Nappia painettu')
    const Object = {
      name: newName,
      number: newNumber,
    }
    axios.post(baseUrl, Object).then((response) => {
      const tila = persons.map((person) => person.name.includes(newName))
      console.log(persons.map((person) => person.name.includes(newName)))
      if (tila.includes(true)) {
        window.alert(`${newName} on jo listassa!!!`)
        setNewName('')
        setNewNumber('')
      } else {
        setPersons(persons.concat(response.data))
        setNewName('')
        setNewNumber('')
        window.location.reload(false)
      }
    })
  }

  return (
    <div>
      <h1>Puhelinluettelo</h1>
      <form onSubmit={addNew}>
        <div>
          nimi: <input value={newName} onChange={handleNewname} />
        </div>
        <div>
          numero: <input value={newNumber} onChange={handleNewnumber} />
        </div>
        <div>
          <button type="submit">Lisää</button>
        </div>
      </form>

      <h2>Numerot</h2>
      <ul>
        {persons.map((person) => (
          <li key={person.name}>
            <div>
              {person.name} {person.number}{' '}
              <button onClick={() => deleteperson(person.id, person.name)}>
                {' '}
                Poista{' '}
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default App
