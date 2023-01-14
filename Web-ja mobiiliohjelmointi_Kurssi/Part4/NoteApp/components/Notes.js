import React from "react";
import { SafeAreaView, StyleSheet, TextInput, View } from "react-native";

const UselessTextInput = () => {
  const [text, setText] = React.useState('');

  return (
    <SafeAreaView>    
        <TextInput
            style={styles.input}
            onChangeText={newText => setText(newText)}
            value={text}
         />  
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  input: {
    height: 40,
    margin: 12,
    borderWidth: 1,
    padding: 10,
  },
});

export default UselessTextInput;