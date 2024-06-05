import { View, StyleSheet } from 'react-native'
import { Button, Text } from '@rneui/base'
import { addNewProductService, createPostService, getAllPostsService, getByUserService, getDocumentTypeService, updatePostService, updateProdcutoService } from '../services/TestServices';


export const TestWebServices = () => {

  const getAllPosts = () => {
    getAllPostsService();
}
const createPost= ()=> {
  createPostService();
}

const updatePost= ()=>{
  updatePostService();
}
const getByUserID=()=>{
  getByUserService();
}
const getAllProduct =()=>{
  getAllPostsService();
}
const addNewProducto=()=>{
  addNewProductService();
}
const updateProducto=()=>{
  updateProdcutoService();
}

// validar servicio documento type

const getDocumentType=()=>{
  getDocumentTypeService();
}
  return <View style={styles.container}>
    <Text style={styles.textContainer}>MODULO 3</Text>
    <View style={styles.buttonContainer}>
      <Button
        title="Recuperar Posts"
        onPress={getAllPosts}
      />
      <Button
        title="Crear Post"
        onPress={createPost}
      />
        <Button
        title="Actualizar Post"
        onPress={updatePost}
      />
        <Button
        title="Filtrar"
        onPress={getByUserID}
      />
          <Button
        title="Obtener productos"
        onPress={getAllProduct}
      />

      <Button
        title="Añadir nuevo producto"
        onPress={addNewProducto}
      />

      <Button
        title="Actualizar Producto"
        onPress={updateProducto}
      />
      <Button
        title="Obetener type document"
        onPress={getDocumentType}
      />
      
    </View>
  </View>
}
const styles = StyleSheet.create({
  container: {
    flex: 1,
    flexDirection: 'column',
    backgroundColor: '#fff',
  },
  textContainer: {
    flex: 1,
    textAlign: 'center',
    fontSize: 18,
    marginVertical: 10
  },
  buttonContainer: {
    flex: 6,
    alignItems: 'stretch',
    justifyContent: 'space-around',
    marginHorizontal:10

  }
});