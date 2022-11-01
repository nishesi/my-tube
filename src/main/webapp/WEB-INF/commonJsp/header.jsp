<%@ page contentType="text/html;charset=UTF-8"%>


<header class="container-fluid">
    <div class="row ">
        <div class="col">
            <div class="container m-1">
                <img src="${requestScope.logoUrl}" class="d-inline" height="50px" width="50px" alt="logo">
                <h1 class="d-inline">${requestScope.appName}</h1>
            </div>
        </div>

        <div class="col-auto text-center m-2">

            <form action="" method="get">
                <div class="input-group flex-nowrap">
                    <input
                            type="text"
                            name="search"
                            class="form-control"
                            placeholder="Search"
                            aria-label="Search"
                    />
                    <button class="btn btn-secondary" type="submit">?</button>
                </div>
            </form>
        </div>

        <div class="col text-end">
            <div class="container m-1">
                <div class="d-inline">${requestScope.firstName} ${requestScope.lastName}</div>
                <img class="d-inline" src="${requestScope.userImgUrl}" height="40px" width="40px" alt="user image">
            </div>
        </div>
    </div>
</header>
