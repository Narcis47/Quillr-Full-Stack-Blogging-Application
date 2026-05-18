(() => {
    if (session?.isLoggedIn()) { window.location.href = 'library.html'; return; }

    const loginCard    = document.getElementById('loginCard');
    const registerCard = document.getElementById('registerCard');
    const goRegister   = document.getElementById('goRegister');
    const goLogin      = document.getElementById('goLogin');
    const loginForm    = document.querySelector('.login-form');
    const registerForm = document.querySelector('.register-form');
    const loginMsg     = document.querySelector('.login-message');
    const registerMsg  = document.querySelector('.register-message');

    /* ── Card switching ── */
    function setActive(activeCard, inactiveCard) {
        activeCard.classList.remove('inactive');
        activeCard.removeAttribute('aria-hidden');
        inactiveCard.classList.add('inactive');
        inactiveCard.setAttribute('aria-hidden', 'true');
        activeCard.querySelectorAll('input, button').forEach(el => { el.removeAttribute('disabled'); el.removeAttribute('tabindex'); });
        inactiveCard.querySelectorAll('input, button').forEach(el => { el.setAttribute('disabled','true'); el.setAttribute('tabindex','-1'); });
        activeCard.querySelector('input:not([disabled])')?.focus();
    }

    registerCard.addEventListener('click', () => { if (registerCard.classList.contains('inactive')) setActive(registerCard, loginCard); });
    loginCard.addEventListener('click',    () => { if (loginCard.classList.contains('inactive'))    setActive(loginCard, registerCard); });
    goRegister?.addEventListener('click', e => { e.stopPropagation(); setActive(registerCard, loginCard); });
    goLogin?.addEventListener('click',    e => { e.stopPropagation(); setActive(loginCard, registerCard); });

    /* ── Helpers ── */
    function showMessage(el, text, isError) {
        el.textContent = text;
        el.style.color = isError ? '#f87171' : '#4ade80';
        el.style.fontSize = '0.8rem';
        el.style.marginTop = '0.5rem';
        el.style.textAlign = 'center';
    }

    function setLoading(btn, loading) {
        btn.disabled = loading;
        btn.textContent = loading ? 'Please wait…' : btn.dataset.label;
    }

    /* ── Login ── */
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = loginForm.querySelector('.btn-primary');
        btn.dataset.label = btn.textContent;
        const email    = document.getElementById('login-email').value.trim();
        const password = document.getElementById('login-password').value;
        if (!email || !password) { showMessage(loginMsg,'Please fill in all fields.',true); return; }
        setLoading(btn, true);
        showMessage(loginMsg,'',false);
        try {
            const data = await api.login(email, password);
            if (data && data.id) {
                session.set(data.id, data.username);
            } else {
                session.set(data.id || localStorage.getItem('userId'), data.username);
            }
            showMessage(loginMsg,'✓ Signed in!',false);
            setTimeout(() => window.location.href='library.html', 700);
        } catch(err) {
            showMessage(loginMsg, err.message||'Something went wrong.', true);
        } finally { setLoading(btn, false); }
    });

    /* ── Register ── */
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const btn = registerForm.querySelector('.btn-primary');
        btn.dataset.label = btn.textContent;
        const username = document.getElementById('reg-username').value.trim();
        const email    = document.getElementById('reg-email').value.trim();
        const password = document.getElementById('reg-password').value;
        if (!username||!email||!password) { showMessage(registerMsg,'Please fill in all fields.',true); return; }
        if (username.length<3)  { showMessage(registerMsg,'Username must be at least 3 characters.',true); return; }
        if (password.length<8)  { showMessage(registerMsg,'Password must be at least 8 characters.',true); return; }
        setLoading(btn, true);
        showMessage(registerMsg,'',false);
        try {
            await api.register(username, email, password);
            showMessage(registerMsg,'✓ Account created! Signing you in…',false);
            // Auto login after register
            const data = await api.login(email, password);
            if (data?.id) session.set(data.id, data.username);
            setTimeout(()=>window.location.href='library.html', 900);
        } catch(err) {
            showMessage(registerMsg, err.message||'Something went wrong.', true);
        } finally { setLoading(btn, false); }
    });
})();
