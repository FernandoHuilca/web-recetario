<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registro Receta</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/framework.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/recipeActions.css">
</head>

<body>
	<div class="recipe-container">
		<h1 class="font-h1">Registrar Receta</h1>

		<form method="POST"
			action="${pageContext.request.contextPath}/RegistrarRecetasController?ruta=guardar"
			enctype="multipart/form-data">
			<!-- <label for="id">Id</label> -->
			<input type="hidden" name="id" id="id"> <label for="name">Nombre*:</label>
			<input type="text" id="name" name="name" required><br>
			<br> <label for="description">Descripción general*:</label><br>
			<textarea id="description" name="description" rows="3" cols="50"
				required></textarea>
			<br>
			<br> <label for="time">Tiempo Preparación (min)*:</label> <input
				type="number" id="time" name="time" min="1" required><br>
			<br> <label for="servings">Porciones*:</label> <input
				type="number" id="servings" name="servings" min="1" required><br>
			<br> <label>Ingredientes*:</label><br>
			<table id="ingredientsTable">
				<thead>
					<tr>
						<th>Ingrediente</th>
						<th>Cantidad</th>
						<th>Unidad</th>
						<th></th>
					</tr>
				</thead>

				<tbody>
					<tr>
						<td><input type="text" name="ingredients_name[]" required></td>
						<td><input type="text" name="ingredients_quantity[]" required></td>
						<td><select name="ingredients_unit[]" required>

								<c:forEach items="${unidades}" var="unidad">
									<option value="${unidad}">${unidad.name()} (${unidad.simbolo})</option>
								</c:forEach>

						</select></td>
						<td>
							<button type="button" class="remove-ingredient">---</button>
						</td>
					</tr>
				</tbody>
			</table>

			<button class="button" type="button" id="addIngredient">Agregar
				ingrediente</button>
			<br> <label for="instructions">Pasos/ Instrucciones*:</label><br>
			<textarea id="instructions" name="instructions" rows="6" cols="50"
				required></textarea>
			<br>
			<br> <label for="image">Imagen:</label> <input type="file"
				id="image" name="image" accept="image/*"><br>
			<br>

			<div class="recipe-actions">
				<input class="button" type="submit" value="Registrar"> <a
					class="button"
					href="${pageContext.request.contextPath}/GestionarRecetasController">Cancelar</a>
			</div>
		</form>
	</div>
<script>
/**
 * 
 */
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