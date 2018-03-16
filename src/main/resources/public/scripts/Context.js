//CONFIG
const TIMEOUT = 10000;
//API Rest
const API_ADD = '/api/add',
    API_GETALL = '/api/getAll',
    API_DELETE = '/api/delete',
    API_SELECT = '/api/select',
    API_UPDATE = 'api/update';
//VARS
var selectAll,
    todoContainer,
    todoList,
    inputField,
    counterField,
    counter = 0,
    filters,
    activeFilter;