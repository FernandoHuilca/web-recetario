<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Gestión de recetas</title>
  <link rel="stylesheet" href="../css/framework.css" />
  <link rel="stylesheet" href="../css/recipeManagement.css" />
</head>

<body class="margin-0 padding-0">
  <header></header>
  <main>
    <div class="return"><a class="general-button text-decoration-none" href="PanelPrincipal.jsp">Volver</a></div>

    <section>
      <div>
        <h1 class="font-h1 text-align-center">Gestión de recetas</h1>
      </div>
      <table>
        <div>
          <thead>
            <tr>
              <td>Nombre</td>
              <td>Descripción</td>
              <td>Imagen</td>
              <td>Actualizar</td>
              <td>Eliminar</td>
            </tr>
          </thead>
        </div>
        <div>
          <tr>
            <td>Receta 1</td>

            <td>Descripción de la receta 1</td>
            <td>
              <img src="../assets/images/common/genericRecipeIcon.png" alt="imagen receta 1" />
            </td>
            <td>
              <button>
                <a href="../pages/FormularioActualizacionRecetas.jsp">
                  <img src="../assets/images/recipeManagement/updateIcon.png" alt="icono actualizar" />
                </a>
              </button>
            </td>
            <td>
              <button>
                <img src="../assets/images/recipeManagement/deleteIcon.png" alt="icono eliminar" />
              </button>
            </td>
          </tr>
        </div>
        <div>
          <tr>
            <td class="column-width-200">Receta 2</td>
            <td class="column-width-200">Descripción de la receta 2</td>
            <td>
              <img src="../assets/images/common/genericRecipeIcon.png" alt="imagen receta 2" />
            </td>
            <td>
              <button>
                <a href="../pages/FormularioActualizacionRecetas.jsp">
                  <img src="../assets/images/recipeManagement/updateIcon.png" alt="icono actualizar" />
                </a>
              </button>
            </td>
            <td>
              <button>
                <img src="../assets/images/recipeManagement/deleteIcon.png" alt="icono eliminar" />
              </button>
            </td>
          </tr>
        </div>
        <div>
          <tr>
            <td>Receta 3</td>

            <td>Descripción de la receta 3</td>
            <td>
              <img src="../assets/images/common/genericRecipeIcon.png" alt="imagen receta 1" />
            </td>
            <td>
              <button>
                <a href="../pages/FormularioActualizacionRecetas.jsp">
                  <img src="../assets/images/recipeManagement/updateIcon.png" alt="icono actualizar" />
                </a>
              </button>
            </td>
            <td>
              <button>
                <img src="../assets/images/recipeManagement/deleteIcon.png" alt="icono eliminar" />
              </button>
            </td>
          </tr>
        </div>
      </table>
      <section>
        <div class="text-align-center margin-20">
          <a class="general-button text-decoration-none" href="../pages/FormularioRegistroRecetas.jsp">Registrar receta</a>
        </div>
      </section>

    </section>
  </main>
</body>

</html>