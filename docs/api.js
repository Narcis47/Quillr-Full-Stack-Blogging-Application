/* ── Quillr API Layer ── */
const api = (() => {
    async function request(method, path, body) {
        const opts = {
            method,
            headers: { 'Content-Type': 'application/json' }
        };
        if (body) opts.body = JSON.stringify(body);
        const res = await fetch(`${API_URL}${path}`, opts);
        const isJson = res.headers.get('Content-Type')?.includes('application/json');
        const data   = isJson ? await res.json() : await res.text();
        if (!res.ok) throw new Error((isJson ? data.message : data) || `HTTP ${res.status}`);
        return data;
    }

    return {
        /* Users */
        register: (username, email, password)  => request('POST', '/api/users/register', { username, email, password }),
        login:    (email, password)             => request('POST', '/api/users/login',    { email, password }),
        getUser:  (id)                          => request('GET',  `/api/users/${id}`),
        getUserByUsername: (username)           => request('GET',  `/api/users/username/${username}`),

        /* Profiles */
        getProfile:            (userId)         => request('GET', `/api/profile/${userId}`),
        getProfileByUsername:  (username)       => request('GET', `/api/profile/username/${username}`),
        updateProfile:         (userId, data)   => request('PUT', `/api/profile/${userId}`, data),

        /* Posts */
        createPost:   (userId, title, content)  => request('POST',   '/api/posts/create', { userId, title, content }),
        getAllPosts:   ()                        => request('GET',    '/api/posts/'),
        getPost:      (id)                      => request('GET',    `/api/posts/${id}`),
        getPostsByUser:(userId)                 => request('GET',    `/api/posts/user/${userId}`),
        getPostsByUsername:(username)           => request('GET',    `/api/posts/user/username/${username}`),
        searchPosts:  (query)                   => request('GET',    `/api/posts/search?query=${encodeURIComponent(query)}`),
        getPagedPosts:(page=0,size=10,sortBy='createdAt') => request('GET', `/api/posts/paged?page=${page}&size=${size}&sortBy=${sortBy}`),
        getUserPagedPosts:(userId,page=0,size=10) => request('GET',  `/api/posts/user/${userId}/paged?page=${page}&size=${size}`),
        updatePost:   (id, title, content)      => request('PUT',    `/api/posts/${id}`, { title, content }),
        deletePost:   (id)                      => request('DELETE', `/api/posts/${id}`),
    };
})();

/* ── Session helpers ── */
const session = {
    get userId()   { return localStorage.getItem('userId'); },
    get username() { return localStorage.getItem('username'); },
    set(id, username) {
        localStorage.setItem('userId', id);
        localStorage.setItem('username', username);
    },
    clear() {
        localStorage.removeItem('userId');
        localStorage.removeItem('username');
    },
    isLoggedIn() { return !!this.userId; },
    requireAuth() {
        if (!this.isLoggedIn()) { window.location.href = 'index.html'; }
    }
};
