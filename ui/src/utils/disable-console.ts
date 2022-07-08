function noop() {
  return undefined;
}

// Disable console.log in contentscript to prevent SES/lockdown logging to external page
if (import.meta.env.NODE_ENV === 'production' && typeof console !== undefined) {
  console.log = noop;
  console.info = noop;
}
