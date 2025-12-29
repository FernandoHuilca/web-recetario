<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Actualización Receta</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/framework.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/recipeActions.css">
</head>

<body>
	<div class="recipe-container">
	
		<h1 class="font-h1">Actualizar Receta</h1>
		
		<form action="${pageContext.request.contextPath}/ActualizarRecetasController?ruta=actualizar" method="POST" enctype="multipart/form-data">
			
			<input type="hidden" name="id" id="id" value="${receta.idReceta}">
			
			<label for="name">Nombre*:</label> <input type="text" id="name"
				name="name" value="${receta.nombre}"><br>
				
			<br> <label for="description">Descripción*:</label><br>
			<textarea id="description" name="description" rows="3" cols="50"
				>${receta.descripcion}</textarea>
			<br>
			
			<br> <label for="time">Tiempo Preparación (min)*:</label> <input
				type="number" id="time" name="time" min="1" value="${receta.tiempoPreparacion}"><br>
				
			<br> <label for="servings">Porciones*:</label> <input
				type="number" id="servings" name="servings" min="1" value="${receta.porciones}"><br>
			
			<br> <label>Ingredientes*:</label><br>
			
			<table id="ingredientsTable">
				<thead>
					<tr>
						<th>Nombre</th>
						<th>Cantidad</th>
						<th>Unidad</th>
						<th></th>
					</tr>
				</thead>

				<tbody>
				
					<c:forEach items="${receta.recetaIngredientes}" var="recetaIngrediente">	
						<tr>
							<td><input type="text" name="ingredients_name[]" value="${recetaIngrediente.ingrediente.nombre}"></td>
							<td><input type="text" name="ingredients_quantity[]" value="${recetaIngrediente.cantidad}"></td>
							<td><select name="ingredients_unit[]">
									<c:forEach items="${unidades}" var="unidad">
										<option value="${unidad}" ${unidad eq recetaIngrediente.unidad ? 'selected' : ''}>${unidad.name()} (${unidad.simbolo})</option>
									</c:forEach>
							</select></td>
							<td>
								<button type="button" class="remove-ingredient">---</button>
							</td>
						</tr>
					</c:forEach>

				</tbody>
			</table>

			<button class="button" type="button" id="addIngredient">Agregar
				ingrediente</button>
				
			<br> <label for="instructions">Pasos*:</label><br>
			<textarea id="instructions" name="instructions" rows="6" cols="50">${receta.descripcionPasos}</textarea>
			<br>
			
			<br> <label for="image">Imagen:</label> <input type="file"
				id="image" name="image" accept="image/*" value="${receta.imagen}"><br>
			<br>

			<div class="recipe-actions">
				<input class="button" type="submit" value="Actualizar"> <a
					class="button" href="${pageContext.request.contextPath}/ActualizarRecetasController?ruta=cancelar&idUsuario=${receta.usuario.idUsuario}">Cancelar</a>
			</div>
			
		</form>
		
	</div>
	
	<script>
		document.addEventListener("DOMContentLoaded", function(){
			const addButton = document.getElementById("addIngredient");
			const tableBody = document.querySelector("#ingredientsTable tbody");
			
			// Agregar ingrediente
			addButton.addEventListener("click", function(){
				const newRow = document.createElement("tr");
				newRow.innerHTML = `
				<td><input type="text" name="ingredients_name[]" required></td>
				                <td><input type="text" name="ingredients_quantity[]" required></td>
				                <td>
				                    <select name="ingredients_unit[]" required>
				                        <c:forEach items="${unidades}" var="unidad">
				                            <option value="${unidad}">${unidad.name()} (${unidad.simbolo})</option>
				                        </c:forEach>
				                    </select>
				                </td>
				                <td>
				                    <button type="button" class="remove-ingredient">---</button>
				                </td>
				`;
				tableBody.appendChild(newRow);
			});
			
			// Eliminar ingrediente (Delegar evento)
			tableBody.addEventListener("click", function(e){
				if(e.target.classList.contains("remove-ingredient")){
					const row = e.target.closest("tr");
					if(tableBody.children.length >1){
						row.remove();
					}else{
						alert("Debe haber al menos un ingrediente");
					}
				}
			});
		});
	</script>
</body>
</html>