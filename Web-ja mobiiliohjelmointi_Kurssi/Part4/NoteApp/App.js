// App.js
import React from 'react'
import NoteText from './components/Notes'
import {
  StyleSheet,
  Text,
  View,
  Keyboard,
  TextInput,
  Button,
  SafeAreaView,
  ScrollView,
} from 'react-native'

const NoteApp = (props) => {
  // initize the state hook
  const [name, setName] = React.useState(props.notes)

  const handletext = (enteredName) => {
    setName(enteredName)
  }

  return (
    <>
      <View
        style={[
          styles.container,
          {
            flexDirection: 'column',
          },
        ]}
      >
        <View style={{ flex: 1 }}>
          <Text style={styles.text}>Notes:</Text>
          <TextInput
            style={styles.input}
            placeholder="Tap here to write new note"
            value={name}
            onChangeText={handletext}
          />
          <Button title="Submit" />
          <View style={styles.result}>
            <Text style={styles.text}></Text>
          </View>
        </View>
        <View style={{ flex: 2, width: 300 }}>
          <ScrollView>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
            <NoteText></NoteText>
          </ScrollView>
        </View>
      </View>
    </>
  )
}

const styles = StyleSheet.create({
  container: {
    marginTop: 100,
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'flex-start',
  },
  text: {
    fontSize: 24,
  },
  input: {
    padding: 10,
    marginVertical: 20,
    width: 200,
    fontSize: 18,
    borderBottomColor: '#ddd',
    borderBottomWidth: 1,
  },
  result: {
    marginTop: 30,
    paddingHorizontal: 30,
    display: 'flex',
  },
  notes: {
    top: -200,
  },
})

export default NoteApp
