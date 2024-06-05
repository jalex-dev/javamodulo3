import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import{PostForm} from './screens/PostForm';
import {TestWebServices} from './screens/TestWebServices'; 

export default function App() {
  const StackProducts = createNativeStackNavigator(); 

  return (
    <NavigationContainer>
      <StackProducts.Navigator initialRouteName="PostFormView">
        <StackProducts.Screen name="TestWebServicesNav" component={TestWebServices} />
        <StackProducts.Screen name="PostFormView" component={PostForm} options={{ title: 'Tipo Documento' }} />
      </StackProducts.Navigator>
    </NavigationContainer>
  );
}
