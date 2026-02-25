```json
{
  "designTokens": {
    "colors": {
      "primary": "#e41f35",
      "primaryForeground": "#ffffff",
      "secondary": "#dedede",
      "secondaryForeground": "#333333",
      "success": "#fde69f",
      "successForeground": "#333333",
      "warning": "#fde69f",
      "warningForeground": "#333333",
      "error": "#e41f35",
      "errorForeground": "#ffffff",
      "info": "#bbdde6",
      "infoForeground": "#333333",
      "background": "#f3f3f3",
      "foreground": "#333333",
      "muted": "#efefef",
      "mutedForeground": "#636363",
      "border": "#efefef",
      "card": "#ffffff",
      "cardForeground": "#333333",
      "active": "#e41f35",
      "activeForeground": "#ffffff",
      "inactive": "#cccccc",
      "inactiveForeground": "#333333",
      "pending": "#9370db",
      "pendingForeground": "#ffffff"
    },
    "typography": {
      "fontFamily": "Geist, Inter, -apple-system, sans-serif",
      "fontScale": "compact",
      "fontSize": {
        "xs": "11px",
        "sm": "12px",
        "base": "13px",
        "lg": "15px",
        "xl": "18px",
        "2xl": "22px",
        "3xl": "28px"
      },
      "fontWeight": {
        "normal": "400",
        "medium": "500",
        "semibold": "600",
        "bold": "700"
      },
      "lineHeight": {
        "tight": "1.25",
        "normal": "1.5",
        "relaxed": "1.75"
      }
    },
    "spacing": {
      "unit": "4px",
      "scale": {
        "xs": "4px",
        "sm": "8px",
        "md": "16px",
        "lg": "24px",
        "xl": "32px",
        "2xl": "48px"
      }
    },
    "borderRadius": {
      "none": "0",
      "sm": "4px",
      "md": "6px",
      "lg": "8px",
      "xl": "12px",
      "full": "9999px"
    },
    "shadows": {
      "sm": "0 1px 2px rgba(0, 0, 0, 0.05)",
      "md": "0 4px 6px rgba(0, 0, 0, 0.1)",
      "lg": "0 10px 15px rgba(0, 0, 0, 0.1)",
      "xl": "0 20px 25px rgba(0, 0, 0, 0.1)"
    }
  },
  "components": {
    "button": {
      "description": "Interactive button component with multiple variants",
      "variants": ["primary", "secondary", "outline", "ghost", "destructive"],
      "htmlExample": "<button class='btn btn-primary'>Primary Button</button><button class='btn btn-secondary'>Secondary</button><button class='btn btn-outline'>Outline</button><button class='btn btn-ghost'>Ghost</button><button class='btn btn-destructive'>Destructive</button>",
      "cssExample": ".btn { padding: var(--spacing-sm) var(--spacing-md); border-radius: var(--radius-md); font-weight: var(--font-weight-medium); cursor: pointer; transition: all 0.2s; display: inline-flex; align-items: center; justify-content: center; gap: var(--spacing-xs); } .btn-primary { background: var(--primary); color: var(--primary-foreground); border: 1px solid var(--primary); } .btn-primary:hover { background: var(--primary-foreground); color: var(--primary); } .btn-secondary { background: var(--secondary); color: var(--secondary-foreground); border: 1px solid var(--secondary); } .btn-secondary:hover { background: var(--muted); } .btn-outline { background: transparent; border: 1px solid var(--border); color: var(--foreground); } .btn-outline:hover { background: var(--muted); } .btn-ghost { background: transparent; color: var(--foreground); } .btn-ghost:hover { background: var(--muted); } .btn-destructive { background: var(--destructive); color: var(--primary-foreground); border: 1px solid var(--destructive); } .btn-destructive:hover { background: var(--primary-foreground); color: var(--destructive); }"
    },
    "alert": {
      "description": "Alert/notification component for status messages",
      "variants": ["success", "warning", "error", "info"],
      "htmlExample": "<div class='alert alert-success'><span class='alert-icon'>✓</span><div><strong>Success</strong><p>Operation completed successfully.</p></div></div><div class='alert alert-warning'><span class='alert-icon'>⚠️</span><div><strong>Warning</strong><p>Please check your inputs.</p></div></div><div class='alert alert-error'><span class='alert-icon'>✕</span><div><strong>Error</strong><p>Operation failed.</p></div></div><div class='alert alert-info'><span class='alert-icon'>ℹ️</span><div><strong>Info</strong><p>This is an informational message.</p></div></div>",
      "cssExample": ".alert { display: flex; gap: var(--spacing-sm); padding: var(--spacing-md); border-radius: var(--radius-md); border: 1px solid; margin-bottom: var(--spacing-sm); } .alert-icon { font-size: var(--font-size-lg); } .alert-success { background: var(--success); color: var(--foreground); border-color: var(--success); } .alert-warning { background: var(--warning); color: var(--foreground); border-color: var(--warning); } .alert-error { background: var(--error); color: var(--primary-foreground); border-color: var(--error); } .alert-info { background: var(--info); color: var(--foreground); border-color: var(--info); }"
    },
    "badge": {
      "description": "Small status indicator badge",
      "variants": ["active", "inactive", "pending"],
      "htmlExample": "<span class='badge badge-active'>Active</span><span class='badge badge-inactive'>Inactive</span><span class='badge badge-pending'>Pending</span>",
      "cssExample": ".badge { display: inline-flex; align-items: center; padding: 2px var(--spacing-sm); border-radius: var(--radius-full); font-size: var(--font-size-xs); font-weight: var(--font-weight-medium); } .badge-active { background: var(--active); color: var(--active-foreground); } .badge-inactive { background: var(--inactive); color: var(--inactive-foreground); } .badge-pending { background: var(--pending); color: var(--pending-foreground); }"
    },
    "input": {
      "description": "Text input field",
      "variants": ["default", "error"],
      "htmlExample": "<div class='input-group'><label class='label' for='email'>Email</label><input type='text' id='email' class='input' placeholder='Enter email'></div><div class='input-group'><label class='label' for='error-input'>Error Input</label><input type='text' id='error-input' class='input input-error' placeholder='Enter email'></div>",
      "cssExample": ".input-group { display: flex; flex-direction: column; margin-bottom: var(--spacing-sm); } .label { font-size: var(--font-size-sm); font-weight: var(--font-weight-semibold); margin-bottom: var(--spacing-xs); } .input { width: 100%; padding: var(--spacing-sm) var(--spacing-md); border: 1px solid var(--border); border-radius: var(--radius-md); font-size: var(--font-size-base); background: var(--card); color: var(--foreground); } .input:focus { outline: none; border-color: var(--primary); } .input-error { border-color: var(--error); }"
    },
    "card": {
      "description": "Content container card",
      "variants": ["default"],
      "htmlExample": "<div class='card'><div class='card-header'><h3 class='card-title'>Card Title</h3><p class='card-description'>Card description goes here</p></div><div class='card-body'><p>Card content</p></div><div class='card-footer'><button class='btn btn-primary'>Action</button></div></div>",
      "cssExample": ".card { background: var(--card); border: 1px solid var(--border); border-radius: var(--radius-md); overflow: hidden; box-shadow: var(--shadow-sm); } .card-header { padding: var(--spacing-lg); border-bottom: 1px solid var(--border); } .card-title { font-size: var(--font-size-xl); font-weight: var(--font-weight-semibold); margin-bottom: var(--spacing-xs); } .card-description { font-size: var(--font-size-base); color: var(--muted-foreground); } .card-body { padding: var(--spacing-lg); } .card-footer { padding: var(--spacing-md) var(--spacing-lg); border-top: 1px solid var(--border); background: var(--muted); display: flex; justify-content: flex-end; }"
    },
    "table": {
      "description": "Data table for list views",
      "variants": ["default"],
      "htmlExample": "<table class='table'><thead><tr><th>Name</th><th>Status</th><th>Date</th></tr></thead><tbody><tr><td>Item 1</td><td><span class='badge badge-active'>Active</span></td><td>2024-02-19</td></tr><tr><td>Item 2</td><td><span class='badge badge-pending'>Pending</span></td><td>2024-02-20</td></tr><tr><td>Item 3</td><td><span class='badge badge-inactive'>Inactive</span></td><td>2024-02-21</td></tr></tbody></table>",
      "cssExample": ".table { width: 100%; border-collapse: collapse; font-size: var(--font-size-base); border: 1px solid var(--border); border-radius: var(--radius-md); overflow: hidden; } .table thead { background: var(--muted); } .table th { text-align: left; padding: var(--spacing-sm) var(--spacing-md); font-weight: var(--font-weight-semibold); border-bottom: 1px solid var(--border); } .table td { padding: var(--spacing-sm) var(--spacing-md); border-bottom: 1px solid var(--border); } .table tr:last-child td { border-bottom: none; }"
    },
    "sidebar": {
      "description": "Collapsible left navigation sidebar. Expands to show icon + label (240px wide); collapses to icon-only rail (56px wide). Toggle controlled by a collapse button at the bottom of the sidebar. Uses CSS transition for smooth animation.",
      "variants": ["expanded", "collapsed"],
      "htmlExample": "<nav class='sidebar' id='sidebar'><div class='sidebar-header'><span class='sidebar-logo'>⚡</span><span class='sidebar-app-name'>HealthBridge Admin</span></div><div class='sidebar-section'><p class='sidebar-section-label'>MAIN</p><a class='sidebar-item active' href='#'><span class='sidebar-icon'>⊞</span><span class='sidebar-label'>Dashboard</span></a><a class='sidebar-item' href='#'><span class='sidebar-icon'>📋</span><span class='sidebar-label'>Programs</span></a><a class='sidebar-item' href='#'><span class='sidebar-icon'>👤</span><span class='sidebar-label'>Users</span></a><a class='sidebar-item' href='#'><span class='sidebar-icon'>⚙</span><span class='sidebar-label'>Settings</span></a></div><button class='sidebar-toggle' onclick=\"document.getElementById('sidebar').classList.toggle('collapsed')\" aria-label='Toggle sidebar'>◀</button></nav>",
      "cssExample": ".sidebar { display: flex; flex-direction: column; width: 240px; min-height: 100vh; background: var(--card); color: var(--foreground); transition: width 0.2s ease; overflow: hidden; flex-shrink: 0; border-right: 1px solid var(--border); } .sidebar.collapsed { width: 56px; } .sidebar-header { display: flex; align-items: center; gap: var(--spacing-sm); padding: var(--spacing-md); height: 56px; border-bottom: 1px solid var(--border); white-space: nowrap; } .sidebar-logo { font-size: 20px; flex-shrink: 0; } .sidebar-app-name { font-size: var(--font-size-sm); font-weight: var(--font-weight-semibold); overflow: hidden; opacity: 1; transition: opacity 0.15s ease; } .sidebar.collapsed .sidebar-app-name { opacity: 0; width: 0; } .sidebar-section { flex: 1; padding: var(--spacing-sm) 0; } .sidebar-section-label { padding: var(--spacing-sm) var(--spacing-md); font-size: var(--font-size-xs); font-weight: var(--font-weight-semibold); color: var(--muted-foreground); white-space: nowrap; overflow: hidden; transition: opacity 0.15s ease; } .sidebar.collapsed .sidebar-section-label { opacity: 0; } .sidebar-item { display: flex; align-items: center; gap: var(--spacing-sm); padding: var(--spacing-sm) var(--spacing-md); font-size: var(--font-size-sm); color: var(--foreground); text-decoration: none; white-space: nowrap; transition: background 0.15s ease, color 0.15s ease; cursor: pointer; border-radius: var(--radius-md); margin: 0 var(--spacing-sm); } .sidebar-item:hover { background: var(--muted); } .sidebar-item.active { background: var(--primary); color: var(--primary-foreground); } .sidebar-icon { font-size: 16px; flex-shrink: 0; width: 24px; text-align: center; } .sidebar-label { overflow: hidden; opacity: 1; transition: opacity 0.15s ease; } .sidebar.collapsed .sidebar-label { opacity: 0; width: 0; } .sidebar-toggle { display: flex; align-items: center; justify-content: center; margin: var(--spacing-sm); padding: var(--spacing-sm); background: var(--muted); border: none; border-radius: var(--radius-md); color: var(--foreground); cursor: pointer; font-size: 12px; transition: background 0.15s ease, transform 0.2s ease; } .sidebar.collapsed .sidebar-toggle { transform: rotate(180deg); } .sidebar-toggle:hover { background: var(--secondary); }"
    },
    "typography": {
      "description": "Typography scale and text styles",
      "variants": ["h1", "h2", "h3", "body", "small"],
      "htmlExample": "<h1 class='heading-1'>Page Title</h1><h2 class='heading-2'>Section Header</h2><h3 class='heading-3'>Sub-section Header</h3><p class='body-text'>Body text paragraph</p><p class='text-sm text-muted'>Small muted text</p>",
      "cssExample": ".heading-1 { font-size: var(--font-size-3xl); font-weight: var(--font-weight-bold); line-height: var(--line-height-tight); margin-bottom: var(--spacing-md); } .heading-2 { font-size: var(--font-size-2xl); font-weight: var(--font-weight-semibold); line-height: var(--line-height-tight); color: var(--primary); margin-bottom: var(--spacing-md); } .heading-3 { font-size: var(--font-size-xl); font-weight: var(--font-weight-medium); line-height: var(--line-height-normal); margin-bottom: var(--spacing-sm); } .body-text { font-size: var(--font-size-base); line-height: var(--line-height-normal); margin-bottom: var(--spacing-sm); } .text-muted { color: var(--muted-foreground); } .text-sm { font-size: var(--font-size-sm); }"
    }
  },
  "explanation": "I customized the Cardinal Brand template for HealthBridge by adding 'active', 'inactive', and 'pending' states with appropriate color associations to represent the different program states. I adjusted the sidebar and card to use white backgrounds, and also made sure the 'h2' typography renders in the brand's primary red color as section headers within the application as the designs indicate. The font size was set to 13px for a more compact and information-dense layout appropriate for enterprise software."
}
```