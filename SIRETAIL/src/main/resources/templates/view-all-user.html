<!DOCTYPE html>
<html lang="en" xmlns:th="http://thymeleaf.org">
<head>
  <title>SiRetail</title>
  <object th:include="fragments//fragment :: css" th:remove="tag"></object>
  <object th:include="fragments//fragment :: js" th:remove="tag"></object>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-F3w7mX95PdgyTmZZMECAngseQB83DfGTowi0iMjiWaeVhAn4FJkqJByhZMI3AhiU" crossorigin="anonymous"></head>

<body>
<nav th:replace="fragments/fragment :: navbar"></nav>
<div class="container">
  <div class="card m-4 p-4">
    <div class="card-body">
      <div class="jususer tify-content-center">
        <h2>Daftar User</h2>
        <br>

        <div th:if="*{listUser.size() !=0}">
          <table class="table">
            <thead>
            <tr>
              <th>No</th>
              <th>Nama</th>
              <th>Username</th>
              <th>Role</th>
              <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <tr th:each="user, iterationStatus : ${listUser}">
              <td th:text="${iterationStatus.count}"></td>
              <td th:text="${user.nama}"></td>
              <td th:text="${user.username}"></td>
              <td th:text="${user.role.nama}"></td>
              <td>
                <a class="btn btn-primary" th:href="@{/user/update/} + ${user.id}" th:if="${role == '[Kepala Retail]'}">Ubah</a>
                <a class="btn btn-primary" th:href="@{/user/update/} + ${user.id}" th:if="${(role == '[Manager Cabang]') && (user.role.nama != 'Kepala Retail')}">Ubah</a>
              </td>
            </tr>
            </tbody>
          </table>
          <div th:if="${role == '[Kepala Retail]'}">
            <a th:href="@{/user/add}" class="mx-2 btn btn-outline-primary">Tambah user</a>
          </div>
        </div>
      </div>
      <br>
    </div>
  </div>
</div>
</body>
</html>