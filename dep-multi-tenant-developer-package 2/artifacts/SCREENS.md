# UI Screens

## Screen Summary

| Screen | Type | Description |
|--------|------|-------------|
| Enrollment Portal Configuration - Upload | wizard | This screen allows the admin to upload a PDF enrollment form and select an AI provider (Claude or Gemini) to generate the form schema. It's the first step in the enrollment portal configuration wizard. |
| Enrollment Portal Configuration - Review Schema | wizard | This screen presents the AI-generated form schema to the admin, allowing them to visually inspect, modify, add, remove, and reorder fields and sections.  It's a critical step for ensuring the accuracy of the digital form. |
| Enrollment Portal Configuration - Branding | wizard | This screen enables admins to configure branding elements for the enrollment portal, including logo, colors, and fonts. It allows for website URL input, logo fetching, and real-time preview. |
| Enrollment Portal Configuration - Program Details | wizard | This screen allows admins to set program details such as the program name, client name, and description, and select the supported workflow types (patient, provider, or both). This information is essential for categorizing and managing enrollment programs. |
| Enrollment Portal Configuration - Publish Confirmation | modal | This confirmation screen appears after the admin clicks the 'Publish' button, confirming that the enrollment portal is live. It displays the generated enrollment portal URL. |
| Public Enrollment Form | form | This screen renders the dynamic enrollment form based on the schema stored in Contentful.  It displays all fields, sections, and branding elements configured in the admin wizard, accessible via a unique URL. |
| Enrollment Program List | list | This screen displays a list of existing enrollment programs in the admin dashboard, showing the client name, logo, program type, and status for each program. It serves as the main navigation point for program management. |
| Admin Program Management Dashboard | dashboard | The main screen of Program Management Dashboard which acts as home screen for managing programs. Shows a list of all Enrollment Programs |
| Admin Login | auth | This screen allows administrators to log in to the admin section of the application using their username and password. It is the entry point for accessing protected admin routes. |

---

## Enrollment Portal Configuration - Upload

**Type:** wizard
**Description:** This screen allows the admin to upload a PDF enrollment form and select an AI provider (Claude or Gemini) to generate the form schema. It's the first step in the enrollment portal configuration wizard.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Portal Configuration - Upload</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSizeXs: 11px;
      --fontSizeSm: 12px;
      --fontSizeBase: 13px;
      --fontSizeLg: 15px;
      --fontSizeXl: 18px;
      --fontSize2xl: 22px;
      --fontSize3xl: 28px;
      --fontWeightNormal: 400;
      --fontWeightMedium: 500;
      --fontWeightSemibold: 600;
      --fontWeightBold: 700;
      --lineHeightTight: 1.25;
      --lineHeightNormal: 1.5;
      --lineHeightRelaxed: 1.75;
      --spacingUnit: 4px;
      --spacingXs: 4px;
      --spacingSm: 8px;
      --spacingMd: 16px;
      --spacingLg: 24px;
      --spacingXl: 32px;
      --spacing2xl: 48px;
      --borderRadiusNone: 0;
      --borderRadiusSm: 4px;
      --borderRadiusMd: 6px;
      --borderRadiusLg: 8px;
      --borderRadiusXl: 12px;
      --borderRadiusFull: 9999px;
      --shadowSm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadowMd: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadowLg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadowXl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      font-size: var(--fontSizeBase);
      color: var(--foreground);
      background-color: var(--background);
      margin: 0;
      padding: var(--spacingLg);
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }

    .container {
      max-width: 960px;
      margin: 0 auto;
      width: 100%;
    }

    .heading-1 {
      font-size: var(--fontSize3xl);
      font-weight: var(--fontWeightBold);
      line-height: var(--lineHeightTight);
      margin-bottom: var(--spacingMd);
    }

    .heading-2 {
      font-size: var(--fontSize2xl);
      font-weight: var(--fontWeightSemibold);
      line-height: var(--lineHeightTight);
      color: var(--primary);
      margin-bottom: var(--spacingMd);
    }

    .body-text {
      font-size: var(--fontSizeBase);
      line-height: var(--lineHeightNormal);
      margin-bottom: var(--spacingSm);
    }

    .text-muted {
      color: var(--mutedForeground);
    }

    .wizard-step {
      margin-bottom: var(--spacingXl);
    }

    .wizard-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacingLg);
    }

    .step-indicator {
      display: flex;
      gap: var(--spacingMd);
    }

    .step {
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .step-number {
      width: 32px;
      height: 32px;
      border-radius: var(--borderRadiusFull);
      background: var(--muted);
      color: var(--mutedForeground);
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: var(--fontWeightMedium);
      margin-bottom: var(--spacingXs);
    }

    .step-number.active {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .step-label {
      font-size: var(--fontSizeSm);
      color: var(--mutedForeground);
    }

    .step-label.active {
      color: var(--foreground);
      font-weight: var(--fontWeightSemibold);
    }

    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadiusMd);
      overflow: hidden;
      box-shadow: var(--shadowSm);
      margin-bottom: var(--spacingLg);
    }

    .card-header {
      padding: var(--spacingLg);
      border-bottom: 1px solid var(--border);
    }

    .card-title {
      font-size: var(--fontSizeXl);
      font-weight: var(--fontWeightSemibold);
      margin-bottom: var(--spacingXs);
    }

    .card-body {
      padding: var(--spacingLg);
    }

    .card-footer {
      padding: var(--spacingMd) var(--spacingLg);
      border-top: 1px solid var(--border);
      background: var(--muted);
      display: flex;
      justify-content: flex-end;
    }

    .input-group {
      display: flex;
      flex-direction: column;
      margin-bottom: var(--spacingSm);
    }

    .label {
      font-size: var(--fontSizeSm);
      font-weight: var(--fontWeightSemibold);
      margin-bottom: var(--spacingXs);
    }

    .input {
      width: 100%;
      padding: var(--spacingSm) var(--spacingMd);
      border: 1px solid var(--border);
      border-radius: var(--borderRadiusMd);
      font-size: var(--fontSizeBase);
      background: var(--card);
      color: var(--foreground);
    }

    .input:focus {
      outline: none;
      border-color: var(--primary);
    }

    .btn {
      padding: var(--spacingSm) var(--spacingMd);
      border-radius: var(--borderRadiusMd);
      font-weight: var(--fontWeightMedium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacingXs);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
      border: 1px solid var(--primary);
    }

    .btn-primary:hover {
      background: var(--primaryForeground);
      color: var(--primary);
    }

    .alert {
      display: flex;
      gap: var(--spacingSm);
      padding: var(--spacingMd);
      border-radius: var(--borderRadiusMd);
      border: 1px solid;
      margin-bottom: var(--spacingSm);
    }

    .alert-icon {
      font-size: var(--fontSizeLg);
    }

    .alert-error {
        background: var(--error);
        color: var(--primaryForeground);
        border-color: var(--error);
    }

    .select {
        width: 100%;
        padding: var(--spacingSm) var(--spacingMd);
        border: 1px solid var(--border);
        border-radius: var(--borderRadiusMd);
        font-size: var(--fontSizeBase);
        background: var(--card);
        color: var(--foreground);
        appearance: none;
        background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'/%3E%3C/svg%3E");
        background-repeat: no-repeat;
        background-position: right var(--spacingMd) center;
        background-size: 1em;
    }

    .select:focus {
        outline: none;
        border-color: var(--primary);
    }

    .upload-area {
        border: 2px dashed var(--muted);
        border-radius: var(--borderRadiusMd);
        padding: var(--spacingXl);
        text-align: center;
        cursor: pointer;
        margin-bottom: var(--spacingLg);
    }

    .upload-area:hover {
        background: var(--muted);
    }

    .upload-icon {
        font-size: var(--fontSize3xl);
        margin-bottom: var(--spacingSm);
    }

    .upload-text {
        font-size: var(--fontSizeBase);
        color: var(--mutedForeground);
    }

    .file-name {
        font-size: var(--fontSizeBase);
        margin-bottom: var(--spacingSm);
    }

    .file-size {
        font-size: var(--fontSizeSm);
        color: var(--mutedForeground);
    }

  </style>
</head>
<body>
  <div class="container">
    <section class="wizard-step">
      <div class="wizard-header">
        <h1 class="heading-1">Enrollment Portal Configuration</h1>
        <div class="step-indicator">
          <div class="step">
            <div class="step-number active">1</div>
            <span class="step-label active">Upload</span>
          </div>
          <div class="step">
            <div class="step-number">2</div>
            <span class="step-label">Review Schema</span>
          </div>
          <div class="step">
            <div class="step-number">3</div>
            <span class="step-label">Branding</span>
          </div>
          <div class="step">
            <div class="step-number">4</div>
            <span class="step-label">Program Details</span>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h2 class="card-title">Upload Enrollment Form</h2>
        </div>
        <div class="card-body">
            <div class="alert alert-error" role="alert">
                <span class="alert-icon">✕</span>
                <div>
                    <strong>Important!</strong> PDF uploads should not exceed 20MB.
                </div>
            </div>
            <div class="upload-area" id="uploadArea">
                <span class="upload-icon">⬆</span>
                <p class="upload-text">Click to upload or drag and drop PDF here</p>
            </div>
            <div id="fileInfo" style="display: none;">
                <p class="file-name" id="fileName"></p>
                <p class="file-size" id="fileSize"></p>
            </div>
          <div class="input-group">
            <label class="label" for="aiProvider">Select AI Provider</label>
            <select class="select" id="aiProvider">
              <option value="claude">Claude</option>
              <option value="gemini">Gemini</option>
            </select>
          </div>
        </div>
        <div class="card-footer">
          <button class="btn btn-primary">Next</button>
        </div>
      </div>
    </section>
  </div>

  <script>
    const uploadArea = document.getElementById('uploadArea');
    const fileInfo = document.getElementById('fileInfo');
    const fileName = document.getElementById('fileName');
    const fileSize = document.getElementById('fileSize');

    uploadArea.addEventListener('click', () => {
        const input = document.createElement('input');
        input.type = 'file';
        input.accept = '.pdf';
        input.onchange = (event) => {
            const file = event.target.files[0];
            displayFileDetails(file);
        };
        input.click();
    });

    uploadArea.addEventListener('dragover', (event) => {
        event.preventDefault();
        uploadArea.classList.add('hover');
    });

    uploadArea.addEventListener('dragleave', () => {
        uploadArea.classList.remove('hover');
    });

    uploadArea.addEventListener('drop', (event) => {
        event.preventDefault();
        uploadArea.classList.remove('hover');
        const file = event.dataTransfer.files[0];
        displayFileDetails(file);
    });

    function displayFileDetails(file) {
        if (file) {
            fileName.textContent = `File Name: ${file.name}`;
            fileSize.textContent = `File Size: ${(file.size / 1024).toFixed(2)} KB`;
            fileInfo.style.display = 'block';
        }
    }
</script>
</body>
</html>
```

---

## Enrollment Portal Configuration - Review Schema

**Type:** wizard
**Description:** This screen presents the AI-generated form schema to the admin, allowing them to visually inspect, modify, add, remove, and reorder fields and sections.  It's a critical step for ensuring the accuracy of the digital form.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Portal Configuration - Review Schema</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;

      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;

      --spacing-unit: 4px;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;

      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;

      --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadow-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadow-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      font-size: var(--fontSize-base);
      color: var(--foreground);
      background-color: var(--background);
      margin: 0;
      padding: 0;
      display: flex;
      flex-direction: column;
      min-height: 100vh;
    }

    .container {
      max-width: 1200px;
      margin: 0 auto;
      padding: var(--spacing-lg);
      flex: 1;
    }

    .heading-1 {
      font-size: var(--fontSize-3xl);
      font-weight: var(--fontWeight-bold);
      line-height: var(--lineHeight-tight);
      margin-bottom: var(--spacing-md);
    }

    .heading-2 {
      font-size: var(--fontSize-2xl);
      font-weight: var(--fontWeight-semibold);
      line-height: var(--lineHeight-tight);
      color: var(--primary);
      margin-bottom: var(--spacing-md);
    }

    .heading-3 {
      font-size: var(--fontSize-xl);
      font-weight: var(--fontWeight-medium);
      line-height: var(--lineHeight-normal);
      margin-bottom: var(--spacing-sm);
    }

    .body-text {
      font-size: var(--fontSize-base);
      line-height: var(--lineHeight-normal);
      margin-bottom: var(--spacing-sm);
    }

    .text-muted {
      color: var(--mutedForeground);
    }

    .text-sm {
      font-size: var(--fontSize-sm);
    }

    /* Button Styles */
    .btn {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-md);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-xs);
      border: none;
      font-family: inherit;
      font-size: var(--fontSize-base);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .btn-primary:hover {
      background: var(--primaryForeground);
      color: var(--primary);
      border: 1px solid var(--primary);
    }

    .btn-secondary {
      background: var(--secondary);
      color: var(--secondaryForeground);
    }

    .btn-secondary:hover {
      background: var(--muted);
    }

    .btn-outline {
      background: transparent;
      border: 1px solid var(--border);
      color: var(--foreground);
    }

    .btn-outline:hover {
      background: var(--muted);
    }

    .btn-ghost {
      background: transparent;
      color: var(--foreground);
    }

    .btn-ghost:hover {
      background: var(--muted);
    }

    .btn-destructive {
      background: var(--error);
      color: var(--primaryForeground);
    }

    .btn-destructive:hover {
      background: var(--primaryForeground);
      color: var(--error);
      border: 1px solid var(--error);
    }

    /* Alert Styles */
    .alert {
      display: flex;
      gap: var(--spacing-sm);
      padding: var(--spacing-md);
      border-radius: var(--borderRadius-md);
      border: 1px solid;
      margin-bottom: var(--spacing-sm);
    }

    .alert-icon {
      font-size: var(--fontSize-lg);
    }

    .alert-success {
      background: var(--success);
      color: var(--successForeground);
      border-color: var(--success);
    }

    .alert-warning {
      background: var(--warning);
      color: var(--warningForeground);
      border-color: var(--warning);
    }

    .alert-error {
      background: var(--error);
      color: var(--errorForeground);
      border-color: var(--error);
    }

    .alert-info {
      background: var(--info);
      color: var(--infoForeground);
      border-color: var(--info);
    }

    /* Badge Styles */
    .badge {
      display: inline-flex;
      align-items: center;
      padding: 2px var(--spacing-sm);
      border-radius: var(--borderRadius-full);
      font-size: var(--fontSize-xs);
      font-weight: var(--fontWeight-medium);
    }

    .badge-active {
      background: var(--active);
      color: var(--activeForeground);
    }

    .badge-inactive {
      background: var(--inactive);
      color: var(--inactiveForeground);
    }

    .badge-pending {
      background: var(--pending);
      color: var(--pendingForeground);
    }

    /* Input Styles */
    .input-group {
      display: flex;
      flex-direction: column;
      margin-bottom: var(--spacing-sm);
    }

    .label {
      font-size: var(--fontSize-sm);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .input {
      width: 100%;
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      font-size: var(--fontSize-base);
      background: var(--card);
      color: var(--foreground);
      font-family: inherit;
    }

    .input:focus {
      outline: none;
      border-color: var(--primary);
    }

    .input-error {
      border-color: var(--error);
    }

    /* Card Styles */
    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      overflow: hidden;
      box-shadow: var(--shadow-sm);
    }

    .card-header {
      padding: var(--spacing-lg);
      border-bottom: 1px solid var(--border);
    }

    .card-title {
      font-size: var(--fontSize-xl);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .card-description {
      font-size: var(--fontSize-base);
      color: var(--mutedForeground);
    }

    .card-body {
      padding: var(--spacing-lg);
    }

    .card-footer {
      padding: var(--spacing-md) var(--spacing-lg);
      border-top: 1px solid var(--border);
      background: var(--muted);
      display: flex;
      justify-content: flex-end;
    }

    /* Table Styles */
    .table {
      width: 100%;
      border-collapse: collapse;
      font-size: var(--fontSize-base);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      overflow: hidden;
    }

    .table thead {
      background: var(--muted);
    }

    .table th {
      text-align: left;
      padding: var(--spacing-sm) var(--spacing-md);
      font-weight: var(--fontWeight-semibold);
      border-bottom: 1px solid var(--border);
    }

    .table td {
      padding: var(--spacing-sm) var(--spacing-md);
      border-bottom: 1px solid var(--border);
    }

    .table tr:last-child td {
      border-bottom: none;
    }

    /* Wizard Styles */
    .wizard-container {
      display: flex;
      flex-direction: column;
      gap: var(--spacing-lg);
    }

    .wizard-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-md);
    }

    .wizard-steps {
      display: flex;
      gap: var(--spacing-sm);
      margin-bottom: var(--spacing-lg);
    }

    .wizard-step {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-full);
      background: var(--muted);
      color: var(--foreground);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: background 0.2s ease;
    }

    .wizard-step.active {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .wizard-step:hover {
      background: var(--secondary);
    }

    .wizard-content {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: var(--spacing-lg);
    }

    .form-section {
      margin-bottom: var(--spacing-lg);
    }

    .form-section-title {
      font-size: var(--fontSize-lg);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-sm);
    }

    .property-editor {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
      gap: var(--spacing-md);
    }

    .property-item {
      display: flex;
      flex-direction: column;
    }

    .property-label {
      font-size: var(--fontSize-sm);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .wizard-footer {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
    }
  </style>
</head>
<body>
  <div class="container">
    <h1 class="heading-1">Enrollment Portal Configuration</h1>
    <div class="wizard-container">
      <div class="wizard-header">
        <h2 class="heading-2">Review Schema</h2>
      </div>
      <div class="wizard-steps">
        <div class="wizard-step">1. Upload PDF</div>
        <div class="wizard-step">2. Review Schema</div>
        <div class="wizard-step">3. Configure Branding</div>
        <div class="wizard-step">4. Program Details</div>
        <div class="wizard-step">5. Publish</div>
      </div>

      <div class="wizard-content">
        <div class="alert alert-info">
          <span class="alert-icon">ℹ️</span>
          <div>
            <strong>Review and Edit</strong>
            <p>Carefully inspect the AI-generated form schema. Modify, add, remove, and reorder fields to ensure accuracy.</p>
          </div>
        </div>

        <div class="form-section">
          <h3 class="form-section-title">Form Fields</h3>
          <div class="property-editor">
            <div class="property-item">
              <label class="property-label">Field Label</label>
              <input type="text" class="input" value="Patient Name">
            </div>
            <div class="property-item">
              <label class="property-label">Field Type</label>
              <select class="input">
                <option>Text</option>
                <option>Number</option>
                <option>Email</option>
                <option>Date</option>
              </select>
            </div>
            <div class="property-item">
              <label class="property-label">Required</label>
              <input type="checkbox">
            </div>
            <div class="property-item">
              <label class="property-label">Placeholder Text</label>
              <input type="text" class="input" value="Enter patient's full name">
            </div>
             <div class="property-item">
              <label class="property-label">Field Label</label>
              <input type="text" class="input" value="Date of Birth">
            </div>
            <div class="property-item">
              <label class="property-label">Field Type</label>
              <select class="input">
                <option>Text</option>
                <option>Number</option>
                <option>Email</option>
                <option>Date</option>
              </select>
            </div>
            <div class="property-item">
              <label class="property-label">Required</label>
              <input type="checkbox" checked>
            </div>
            <div class="property-item">
              <label class="property-label">Placeholder Text</label>
              <input type="text" class="input" value="MM/DD/YYYY">
            </div>

          </div>
        </div>

        <div class="form-section">
          <h3 class="form-section-title">Section: Insurance Information</h3>
          <div class="property-editor">
               <div class="property-item">
              <label class="property-label">Field Label</label>
              <input type="text" class="input" value="Insurance Provider">
            </div>
            <div class="property-item">
              <label class="property-label">Field Type</label>
              <select class="input">
                <option>Text</option>
                <option>Number</option>
                <option>Email</option>
                <option>Date</option>
              </select>
            </div>
            <div class="property-item">
              <label class="property-label">Required</label>
              <input type="checkbox">
            </div>
            <div class="property-item">
              <label class="property-label">Placeholder Text</label>
              <input type="text" class="input" value="Enter insurance provider name">
            </div>

          </div>
        </div>
      </div>

      <div class="wizard-footer">
        <button class="btn btn-secondary">Back</button>
        <button class="btn btn-primary">Next</button>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Enrollment Portal Configuration - Branding

**Type:** wizard
**Description:** This screen enables admins to configure branding elements for the enrollment portal, including logo, colors, and fonts. It allows for website URL input, logo fetching, and real-time preview.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Portal Configuration - Branding</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;
      --spacing-unit: 4px;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;
      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;
      --shadows-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadows-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadows-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadows-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      font-size: var(--fontSize-base);
      color: var(--foreground);
      background-color: var(--background);
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
    }

    .container {
      width: 100%;
      max-width: 1200px;
      padding: var(--spacing-lg);
    }

    .wizard {
      display: flex;
      flex-direction: column;
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      box-shadow: var(--shadows-sm);
      overflow: hidden;
    }

    .wizard-header {
      padding: var(--spacing-lg);
      border-bottom: 1px solid var(--border);
      text-align: center;
    }

    .wizard-title {
      font-size: var(--fontSize-2xl);
      font-weight: var(--fontWeight-semibold);
      color: var(--primary);
      margin-bottom: var(--spacing-sm);
    }

    .wizard-steps {
      display: flex;
      justify-content: space-around;
      padding: var(--spacing-md);
      border-bottom: 1px solid var(--border);
    }

    .wizard-step {
      position: relative;
      flex: 1;
      text-align: center;
    }

    .wizard-step::before {
      content: '';
      position: absolute;
      top: 50%;
      left: 0;
      right: 0;
      height: 1px;
      background: var(--border);
      transform: translateY(-50%);
      z-index: 0;
    }

    .wizard-step:first-child::before {
      left: 50%;
    }

    .wizard-step:last-child::before {
      right: 50%;
    }

    .wizard-step-button {
      position: relative;
      z-index: 1;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      width: 32px;
      height: 32px;
      border-radius: var(--borderRadius-full);
      background: var(--muted);
      color: var(--mutedForeground);
      border: none;
      cursor: pointer;
    }

    .wizard-step-button.active {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .wizard-step-label {
      font-size: var(--fontSize-sm);
      margin-top: var(--spacing-xs);
      color: var(--mutedForeground);
    }

    .wizard-step-button.active + .wizard-step-label {
      color: var(--foreground);
    }

    .wizard-content {
      padding: var(--spacing-lg);
    }

    .form-group {
      margin-bottom: var(--spacing-md);
    }

    .form-label {
      display: block;
      font-size: var(--fontSize-sm);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .input {
      width: 100%;
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      font-size: var(--fontSize-base);
      background: var(--card);
      color: var(--foreground);
    }

    .color-picker {
      width: 100%;
      height: 40px;
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      margin-top: var(--spacing-xs);
    }

    .logo-preview {
      max-width: 200px;
      margin-top: var(--spacing-md);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
    }

    .live-preview {
        border: 1px solid var(--border);
        border-radius: var(--borderRadius-md);
        margin-top: var(--spacing-lg);
        padding: var(--spacing-md);
        background: var(--muted);
    }

    .live-preview h3 {
        font-size: var(--fontSize-lg);
        margin-bottom: var(--spacing-sm);
    }

    .wizard-footer {
      padding: var(--spacing-lg);
      border-top: 1px solid var(--border);
      display: flex;
      justify-content: space-between;
    }

    .btn {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-md);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-xs);
      border: none;
      font-size: var(--fontSize-base);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
      border: 1px solid var(--primary);
    }

    .btn-primary:hover {
      background: var(--primaryForeground);
      color: var(--primary);
    }

    .btn-secondary {
      background: var(--secondary);
      color: var(--secondaryForeground);
      border: 1px solid var(--secondary);
    }

    .btn-secondary:hover {
      background: var(--muted);
    }

    /* Responsive Styles */
    @media (max-width: 768px) {
      .container {
        padding: var(--spacing-md);
      }

      .wizard-steps {
        flex-direction: column;
        align-items: center;
      }

      .wizard-step {
        margin-bottom: var(--spacing-lg);
      }

      .wizard-step::before {
        display: none;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="wizard">
      <div class="wizard-header">
        <h2 class="wizard-title">Enrollment Portal Configuration</h2>
      </div>

      <div class="wizard-steps">
        <div class="wizard-step">
          <button class="wizard-step-button">1</button>
          <div class="wizard-step-label">Schema</div>
        </div>
        <div class="wizard-step">
          <button class="wizard-step-button active">2</button>
          <div class="wizard-step-label">Branding</div>
        </div>
        <div class="wizard-step">
          <button class="wizard-step-button">3</button>
          <div class="wizard-step-label">Program Details</div>
        </div>
        <div class="wizard-step">
          <button class="wizard-step-button">4</button>
          <div class="wizard-step-label">Publish</div>
        </div>
      </div>

      <div class="wizard-content">
        <h2 class="heading-2">Configure Branding</h2>

        <div class="form-group">
          <label class="form-label" for="websiteUrl">Website URL</label>
          <input type="url" id="websiteUrl" class="input" placeholder="Enter website URL to fetch logo and colors">
        </div>

        <div class="form-group">
          <label class="form-label" for="logoUrl">Logo URL</label>
          <input type="url" id="logoUrl" class="input" placeholder="Or enter logo URL directly">
          <img id="logoPreview" class="logo-preview" src="" alt="Logo Preview">
        </div>

        <div class="form-group">
          <label class="form-label">Primary Color</label>
          <input type="color" class="color-picker" value="#e41f35">
        </div>

        <div class="form-group">
          <label class="form-label">Secondary Color</label>
          <input type="color" class="color-picker" value="#dedede">
        </div>

        <div class="form-group">
          <label class="form-label">Accent Color</label>
          <input type="color" class="color-picker" value="#bbdde6">
        </div>

        <div class="form-group">
            <label class="form-label">Font Preference</label>
            <select class="input">
                <option>Geist</option>
                <option>Inter</option>
                <option>Arial</option>
                <option>Helvetica</option>
            </select>
        </div>

        <div class="live-preview">
            <h3>Live Preview</h3>
            <p>This is a preview of how the enrollment form will look with the applied branding.</p>
            <button class="btn btn-primary">Submit</button>
        </div>
      </div>

      <div class="wizard-footer">
        <button class="btn btn-secondary">Previous</button>
        <button class="btn btn-primary">Next</button>
      </div>
    </div>
  </div>

  <script>
    const websiteUrlInput = document.getElementById('websiteUrl');
    const logoUrlInput = document.getElementById('logoUrl');
    const logoPreview = document.getElementById('logoPreview');

    logoUrlInput.addEventListener('input', function() {
      logoPreview.src = this.value;
    });
  </script>
</body>
</html>
```

---

## Enrollment Portal Configuration - Program Details

**Type:** wizard
**Description:** This screen allows admins to set program details such as the program name, client name, and description, and select the supported workflow types (patient, provider, or both). This information is essential for categorizing and managing enrollment programs.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Portal Configuration - Program Details</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;
      --spacing-unit: 4px;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;
      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;
      --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadow-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadow-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      background-color: var(--background);
      color: var(--foreground);
      line-height: var(--lineHeight-normal);
      font-size: var(--fontSize-base);
      margin: 0;
      padding: var(--spacing-md);
    }

    .container {
      max-width: 960px;
      margin: 0 auto;
    }

    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      overflow: hidden;
      box-shadow: var(--shadow-sm);
      margin-bottom: var(--spacing-lg);
    }

    .card-header {
      padding: var(--spacing-lg);
      border-bottom: 1px solid var(--border);
    }

    .card-title {
      font-size: var(--fontSize-xl);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .card-body {
      padding: var(--spacing-lg);
    }

    .form-group {
      margin-bottom: var(--spacing-md);
    }

    label {
      display: block;
      font-size: var(--fontSize-sm);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    input[type="text"],
    textarea,
    select {
      width: 100%;
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      font-size: var(--fontSize-base);
      background: var(--card);
      color: var(--foreground);
      box-sizing: border-box;
    }

    input[type="checkbox"] {
      margin-right: var(--spacing-xs);
    }

    .workflow-types {
      display: flex;
      flex-direction: column;
    }

    .workflow-types label {
      font-weight: normal;
      margin-bottom: var(--spacing-xs);
    }

    .wizard-progress {
      display: flex;
      justify-content: space-between;
      margin-bottom: var(--spacing-lg);
    }

    .wizard-step {
      flex: 1;
      text-align: center;
      padding: var(--spacing-sm);
      border-bottom: 2px solid var(--border);
      color: var(--muted-foreground);
    }

    .wizard-step.active {
      border-bottom-color: var(--primary);
      color: var(--primary);
      font-weight: var(--fontWeight-semibold);
    }

    .wizard-actions {
      display: flex;
      justify-content: flex-end;
      gap: var(--spacing-md);
      margin-top: var(--spacing-lg);
    }

    .btn {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-md);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-xs);
      border: none;
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .btn-primary:hover {
      background: #bb2030; /* Darker shade for hover */
    }

    .btn-secondary {
      background: var(--secondary);
      color: var(--secondaryForeground);
    }

    .btn-secondary:hover {
      background: var(--muted);
    }

    h2 {
        font-size: var(--fontSize-2xl);
        font-weight: var(--fontWeight-semibold);
        line-height: var(--lineHeight-tight);
        color: var(--primary);
        margin-bottom: var(--spacing-md);
    }

  </style>
</head>
<body>
  <div class="container">
    <div class="card">
      <div class="card-header">
        <h2 class="card-title">Enrollment Portal Configuration</h2>
      </div>
      <div class="card-body">
        <div class="wizard-progress">
          <div class="wizard-step">Upload Form</div>
          <div class="wizard-step">Review Schema</div>
          <div class="wizard-step active">Program Details</div>
          <div class="wizard-step">Branding</div>
        </div>

        <h2 class="heading-2">Program Details</h2>

        <form>
          <div class="form-group">
            <label for="programName">Program Name</label>
            <input type="text" id="programName" placeholder="Enter program name">
          </div>

          <div class="form-group">
            <label for="clientName">Client Name</label>
            <input type="text" id="clientName" placeholder="Enter client name">
          </div>

          <div class="form-group">
            <label for="description">Description</label>
            <textarea id="description" rows="4" placeholder="Enter program description"></textarea>
          </div>

          <div class="form-group">
            <label>Supported Workflow Types</label>
            <div class="workflow-types">
              <label><input type="checkbox" name="workflowType" value="patient">Patient Enrollment</label>
              <label><input type="checkbox" name="workflowType" value="provider">Provider Enrollment</label>
              <label><input type="checkbox" name="workflowType" value="both">Both (Patient & Provider)</label>
            </div>
          </div>

          <div class="wizard-actions">
            <button class="btn btn-secondary">Back</button>
            <button class="btn btn-primary">Next</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Enrollment Portal Configuration - Publish Confirmation

**Type:** modal
**Description:** This confirmation screen appears after the admin clicks the 'Publish' button, confirming that the enrollment portal is live. It displays the generated enrollment portal URL.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Portal Configuration - Publish Confirmation</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --unit: 4px;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;
      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;
      --shadows-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadows-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadows-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadows-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      background-color: var(--background);
      color: var(--foreground);
      line-height: var(--lineHeight-normal);
      margin: 0;
      padding: var(--spacing-lg);
    }

    .modal-overlay {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      display: flex;
      justify-content: center;
      align-items: center;
      z-index: 1000;
    }

    .modal {
      background-color: var(--card);
      border-radius: var(--borderRadius-md);
      box-shadow: var(--shadows-md);
      max-width: 600px;
      width: 100%;
      padding: var(--spacing-lg);
    }

    .modal-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: var(--spacing-md);
    }

    .modal-title {
      font-size: var(--fontSize-xl);
      font-weight: var(--fontWeight-semibold);
    }

    .modal-close-button {
      background: none;
      border: none;
      font-size: var(--fontSize-lg);
      cursor: pointer;
      color: var(--mutedForeground);
    }

    .modal-body {
      margin-bottom: var(--spacing-lg);
    }

    .modal-footer {
      display: flex;
      justify-content: flex-end;
    }

    .btn {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-md);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-xs);
      border: none;
      font-family: var(--fontFamily);
      font-size: var(--fontSize-base);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .btn-primary:hover {
      background: var(--primaryForeground);
      color: var(--primary);
      border: 1px solid var(--primary);
    }

    .body-text {
      font-size: var(--fontSize-base);
      line-height: var(--lineHeight-normal);
      margin-bottom: var(--spacing-sm);
    }
  </style>
</head>
<body>
  <div class="modal-overlay">
    <div class="modal">
      <div class="modal-header">
        <h2 class="modal-title">Enrollment Portal Published</h2>
        <button class="modal-close-button">×</button>
      </div>
      <div class="modal-body">
        <p class="body-text">Your enrollment portal is now live and accessible via the following URL:</p>
        <p class="body-text"><strong><a href="/enroll/12345" target="_blank">https://healthbridge.com/enroll/12345</a></strong></p>
        <p class="body-text">Share this link with your clients to start the enrollment process.</p>
      </div>
      <div class="modal-footer">
        <button class="btn btn-primary">Go to Enrollment Portal</button>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Public Enrollment Form

**Type:** form
**Description:** This screen renders the dynamic enrollment form based on the schema stored in Contentful.  It displays all fields, sections, and branding elements configured in the admin wizard, accessible via a unique URL.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Public Enrollment Form</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSizeXs: 11px;
      --fontSizeSm: 12px;
      --fontSizeBase: 13px;
      --fontSizeLg: 15px;
      --fontSizeXl: 18px;
      --fontSize2xl: 22px;
      --fontSize3xl: 28px;
      --fontWeightNormal: 400;
      --fontWeightMedium: 500;
      --fontWeightSemibold: 600;
      --fontWeightBold: 700;
      --lineHeightTight: 1.25;
      --lineHeightNormal: 1.5;
      --lineHeightRelaxed: 1.75;
      --spacingUnit: 4px;
      --spacingXs: 4px;
      --spacingSm: 8px;
      --spacingMd: 16px;
      --spacingLg: 24px;
      --spacingXl: 32px;
      --spacing2xl: 48px;
      --borderRadiusNone: 0;
      --borderRadiusSm: 4px;
      --borderRadiusMd: 6px;
      --borderRadiusLg: 8px;
      --borderRadiusXl: 12px;
      --borderRadiusFull: 9999px;
      --shadowsSm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadowsMd: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadowsLg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadowsXl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      font-size: var(--fontSizeBase);
      color: var(--foreground);
      background: var(--background);
      margin: 0;
      padding: var(--spacingMd);
      display: flex;
      justify-content: center;
    }

    .container {
      width: 100%;
      max-width: 960px;
    }

    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadiusMd);
      overflow: hidden;
      box-shadow: var(--shadowsSm);
      margin-bottom: var(--spacingMd);
    }

    .card-header {
      padding: var(--spacingLg);
      border-bottom: 1px solid var(--border);
    }

    .card-title {
      font-size: var(--fontSizeXl);
      font-weight: var(--fontWeightSemibold);
      margin-bottom: var(--spacingXs);
      color: var(--primary);
    }

    .card-description {
      font-size: var(--fontSizeBase);
      color: var(--mutedForeground);
    }

    .card-body {
      padding: var(--spacingLg);
    }

    .card-footer {
      padding: var(--spacingMd) var(--spacingLg);
      border-top: 1px solid var(--border);
      background: var(--muted);
      display: flex;
      justify-content: flex-end;
    }

    .input-group {
      display: flex;
      flex-direction: column;
      margin-bottom: var(--spacingSm);
    }

    .label {
      font-size: var(--fontSizeSm);
      font-weight: var(--fontWeightSemibold);
      margin-bottom: var(--spacingXs);
    }

    .input {
      width: 100%;
      padding: var(--spacingSm) var(--spacingMd);
      border: 1px solid var(--border);
      border-radius: var(--borderRadiusMd);
      font-size: var(--fontSizeBase);
      background: var(--card);
      color: var(--foreground);
    }

    .input:focus {
      outline: none;
      border-color: var(--primary);
    }

    .input-error {
      border-color: var(--error);
    }

    .btn {
      padding: var(--spacingSm) var(--spacingMd);
      border-radius: var(--borderRadiusMd);
      font-weight: var(--fontWeightMedium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacingXs);
      border: none;
      font-family: var(--fontFamily);
      font-size: var(--fontSizeBase);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primaryForeground);
    }

    .btn-primary:hover {
      background: var(--primaryForeground);
      color: var(--primary);
      border: 1px solid var(--primary);
    }

    .btn-secondary {
      background: var(--secondary);
      color: var(--secondaryForeground);
    }

    .btn-secondary:hover {
      background: var(--muted);
    }

    .form-section {
      margin-bottom: var(--spacingLg);
      border-bottom: 1px solid var(--border);
      padding-bottom: var(--spacingLg);
    }

    .form-section:last-child {
      border-bottom: none;
    }

    .form-section h2 {
      font-size: var(--fontSize2xl);
      font-weight: var(--fontWeightSemibold);
      color: var(--primary);
      margin-bottom: var(--spacingMd);
    }

    .grid-container {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
        gap: var(--spacingMd);
    }

    /* Responsive adjustments */
    @media (max-width: 768px) {
      .container {
        padding: var(--spacingSm);
      }
      .grid-container {
          grid-template-columns: 1fr;
      }
    }
  </style>
</head>
<body>
  <div class="container">
    <div class="card">
      <div class="card-header">
        <h1 class="card-title">Enrollment Form</h1>
        <p class="card-description">Please fill out the form below to enroll.</p>
      </div>
      <div class="card-body">

        <div class="form-section">
          <h2>Personal Information</h2>
          <div class="grid-container">
            <div class="input-group">
              <label class="label" for="firstName">First Name</label>
              <input type="text" id="firstName" class="input" placeholder="Enter first name">
            </div>

            <div class="input-group">
              <label class="label" for="lastName">Last Name</label>
              <input type="text" id="lastName" class="input" placeholder="Enter last name">
            </div>
          </div>

          <div class="input-group">
            <label class="label" for="dob">Date of Birth</label>
            <input type="date" id="dob" class="input">
          </div>

          <div class="input-group">
            <label class="label" for="email">Email Address</label>
            <input type="email" id="email" class="input" placeholder="Enter email address">
          </div>

          <div class="input-group">
            <label class="label" for="phone">Phone Number</label>
            <input type="tel" id="phone" class="input" placeholder="Enter phone number">
          </div>
        </div>

        <div class="form-section">
          <h2>Address Information</h2>
          <div class="input-group">
            <label class="label" for="address1">Address Line 1</label>
            <input type="text" id="address1" class="input" placeholder="Enter address">
          </div>
          <div class="input-group">
            <label class="label" for="address2">Address Line 2</label>
            <input type="text" id="address2" class="input" placeholder="Enter apartment, unit, etc. (optional)">
          </div>
          <div class="grid-container">
            <div class="input-group">
              <label class="label" for="city">City</label>
              <input type="text" id="city" class="input" placeholder="Enter city">
            </div>
            <div class="input-group">
              <label class="label" for="state">State</label>
              <select id="state" class="input">
                <option value="">Select a state</option>
                <option value="AL">Alabama</option>
                <option value="AK">Alaska</option>
                <!-- Add more states here -->
              </select>
            </div>
            <div class="input-group">
              <label class="label" for="zip">Zip Code</label>
              <input type="text" id="zip" class="input" placeholder="Enter zip code">
            </div>
          </div>
        </div>

        <div class="form-section">
          <h2>Insurance Information</h2>
          <div class="input-group">
            <label class="label" for="insuranceProvider">Insurance Provider</label>
            <input type="text" id="insuranceProvider" class="input" placeholder="Enter insurance provider">
          </div>
          <div class="input-group">
            <label class="label" for="policyNumber">Policy Number</label>
            <input type="text" id="policyNumber" class="input" placeholder="Enter policy number">
          </div>
          <div class="input-group">
            <label class="label" for="groupNumber">Group Number</label>
            <input type="text" id="groupNumber" class="input" placeholder="Enter group number">
          </div>
        </div>

        <div class="form-section">
          <h2>Terms and Conditions</h2>
          <div class="input-group">
            <input type="checkbox" id="terms" name="terms">
            <label class="label" for="terms">I agree to the terms and conditions.</label>
          </div>
        </div>

      </div>
      <div class="card-footer">
        <button class="btn btn-primary">Submit</button>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Enrollment Program List

**Type:** list
**Description:** This screen displays a list of existing enrollment programs in the admin dashboard, showing the client name, logo, program type, and status for each program. It serves as the main navigation point for program management.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Enrollment Program List</title>
  <style>
    :root {
      --primary: #e41f35;
      --primaryForeground: #ffffff;
      --secondary: #dedede;
      --secondaryForeground: #333333;
      --success: #fde69f;
      --successForeground: #333333;
      --warning: #fde69f;
      --warningForeground: #333333;
      --error: #e41f35;
      --errorForeground: #ffffff;
      --info: #bbdde6;
      --infoForeground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --mutedForeground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --cardForeground: #333333;
      --active: #e41f35;
      --activeForeground: #ffffff;
      --inactive: #cccccc;
      --inactiveForeground: #333333;
      --pending: #9370db;
      --pendingForeground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;
      --spacing-unit: 4px;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;
      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;
      --shadows-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadows-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadows-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadows-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      font-size: var(--fontSize-base);
      color: var(--foreground);
      background: var(--background);
      margin: 0;
      padding: var(--spacing-md);
    }

    .container {
      max-width: 1440px;
      margin: 0 auto;
    }

    .heading-2 {
      font-size: var(--fontSize-2xl);
      font-weight: var(--fontWeight-semibold);
      line-height: var(--lineHeight-tight);
      color: var(--primary);
      margin-bottom: var(--spacing-md);
    }

    .card-list {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
      gap: var(--spacing-md);
    }

    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      overflow: hidden;
      box-shadow: var(--shadows-sm);
      display: flex;
      flex-direction: column;
      justify-content: space-between;
      padding: var(--spacing-md);
    }

    .card-header {
      margin-bottom: var(--spacing-sm);
    }

    .card-title {
      font-size: var(--fontSize-lg);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .card-client {
      font-size: var(--fontSize-base);
      color: var(--mutedForeground);
    }

    .card-body {
      flex-grow: 1;
    }

    .card-footer {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-top: var(--spacing-md);
    }

    .program-type {
      font-size: var(--fontSize-sm);
      color: var(--mutedForeground);
    }

    .badge {
      display: inline-flex;
      align-items: center;
      padding: 2px var(--spacing-sm);
      border-radius: var(--borderRadius-full);
      font-size: var(--fontSize-xs);
      font-weight: var(--fontWeight-medium);
    }

    .badge-active {
      background: var(--active);
      color: var(--activeForeground);
    }

    .badge-inactive {
      background: var(--inactive);
      color: var(--inactiveForeground);
    }

    .badge-pending {
      background: var(--pending);
      color: var(--pendingForeground);
    }

    .card-actions {
        display: flex;
        gap: var(--spacing-sm);
    }

    .card-actions button {
        padding: var(--spacing-xs) var(--spacing-sm);
        border: none;
        border-radius: var(--borderRadius-md);
        cursor: pointer;
        font-size: var(--fontSize-sm);
    }

    .card-actions .edit-button {
        background-color: var(--secondary);
        color: var(--secondaryForeground);
    }

     .card-actions .edit-button:hover {
        background-color: var(--muted);
    }

    .card-actions .view-button {
        background-color: var(--info);
        color: var(--infoForeground);
    }
    .card-actions .view-button:hover {
        background-color: #b0d1da;
    }

  </style>
</head>
<body>
  <div class="container">
    <h2 class="heading-2">Enrollment Programs</h2>

    <div class="card-list">
      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Medicare Enrollment 2024</h3>
          <p class="card-client">Aetna</p>
        </div>
        <div class="card-body">
          <p class="program-type">Patient Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-active">Active</span>
          <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Medicaid Provider Enrollment</h3>
          <p class="card-client">UnitedHealthcare</p>
        </div>
        <div class="card-body">
          <p class="program-type">Provider Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-pending">Pending</span>
           <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Commercial Plan Enrollment</h3>
          <p class="card-client">Cigna</p>
        </div>
        <div class="card-body">
          <p class="program-type">Patient Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-inactive">Inactive</span>
           <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h3 class="card-title">State Exchange Enrollment</h3>
          <p class="card-client">Blue Cross Blue Shield</p>
        </div>
        <div class="card-body">
          <p class="program-type">Patient Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-active">Active</span>
           <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Dental Provider Credentialing</h3>
          <p class="card-client">Delta Dental</p>
        </div>
        <div class="card-body">
          <p class="program-type">Provider Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-active">Active</span>
           <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          <h3 class="card-title">Vision Plan Enrollment</h3>
          <p class="card-client">VSP</p>
        </div>
        <div class="card-body">
          <p class="program-type">Patient Enrollment</p>
        </div>
        <div class="card-footer">
          <span class="badge badge-inactive">Inactive</span>
           <div class="card-actions">
            <button class="edit-button">Edit</button>
            <button class="view-button">View</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
```

---

## Admin Program Management Dashboard

**Type:** dashboard
**Description:** The main screen of Program Management Dashboard which acts as home screen for managing programs. Shows a list of all Enrollment Programs

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Program Management Dashboard</title>
    <style>
        :root {
            --primary: #e41f35;
            --primary-foreground: #ffffff;
            --secondary: #dedede;
            --secondary-foreground: #333333;
            --success: #fde69f;
            --successForeground: #333333;
            --warning: #fde69f;
            --warningForeground: #333333;
            --error: #e41f35;
            --errorForeground: #ffffff;
            --info: #bbdde6;
            --infoForeground: #333333;
            --background: #f3f3f3;
            --foreground: #333333;
            --muted: #efefef;
            --mutedForeground: #636363;
            --border: #efefef;
            --card: #ffffff;
            --cardForeground: #333333;
            --active: #e41f35;
            --activeForeground: #ffffff;
            --inactive: #cccccc;
            --inactiveForeground: #333333;
            --pending: #9370db;
            --pendingForeground: #ffffff;
            --fontFamily: Geist, Inter, -apple-system, sans-serif;
            --fontSize-xs: 11px;
            --fontSize-sm: 12px;
            --fontSize-base: 13px;
            --fontSize-lg: 15px;
            --fontSize-xl: 18px;
            --fontSize-2xl: 22px;
            --fontSize-3xl: 28px;
            --fontWeight-normal: 400;
            --fontWeight-medium: 500;
            --fontWeight-semibold: 600;
            --fontWeight-bold: 700;
            --lineHeight-tight: 1.25;
            --lineHeight-normal: 1.5;
            --lineHeight-relaxed: 1.75;
            --spacing-unit: 4px;
            --spacing-xs: 4px;
            --spacing-sm: 8px;
            --spacing-md: 16px;
            --spacing-lg: 24px;
            --spacing-xl: 32px;
            --spacing-2xl: 48px;
            --borderRadius-none: 0;
            --borderRadius-sm: 4px;
            --borderRadius-md: 6px;
            --borderRadius-lg: 8px;
            --borderRadius-xl: 12px;
            --borderRadius-full: 9999px;
            --shadow-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
            --shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
            --shadow-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
            --shadow-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
        }

        body {
            font-family: var(--fontFamily);
            background-color: var(--background);
            color: var(--foreground);
            margin: 0;
            padding: 0;
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            display: flex;
            flex-direction: column;
            width: 240px;
            min-height: 100vh;
            background: var(--card);
            color: var(--foreground);
            transition: width 0.2s ease;
            overflow: hidden;
            flex-shrink: 0;
            border-right: 1px solid var(--border);
        }

        .sidebar.collapsed {
            width: 56px;
        }

        .sidebar-header {
            display: flex;
            align-items: center;
            gap: var(--spacing-sm);
            padding: var(--spacing-md);
            height: 56px;
            border-bottom: 1px solid var(--border);
            white-space: nowrap;
        }

        .sidebar-logo {
            font-size: 20px;
            flex-shrink: 0;
        }

        .sidebar-app-name {
            font-size: var(--font-size-sm);
            font-weight: var(--fontWeight-semibold);
            overflow: hidden;
            opacity: 1;
            transition: opacity 0.15s ease;
        }

        .sidebar.collapsed .sidebar-app-name {
            opacity: 0;
            width: 0;
        }

        .sidebar-section {
            flex: 1;
            padding: var(--spacing-sm) 0;
        }

        .sidebar-section-label {
            padding: var(--spacing-sm) var(--spacing-md);
            font-size: var(--font-size-xs);
            font-weight: var(--fontWeight-semibold);
            color: var(--mutedForeground);
            white-space: nowrap;
            overflow: hidden;
            transition: opacity 0.15s ease;
        }

        .sidebar.collapsed .sidebar-section-label {
            opacity: 0;
        }

        .sidebar-item {
            display: flex;
            align-items: center;
            gap: var(--spacing-sm);
            padding: var(--spacing-sm) var(--spacing-md);
            font-size: var(--font-size-sm);
            color: var(--foreground);
            text-decoration: none;
            white-space: nowrap;
            transition: background 0.15s ease, color 0.15s ease;
            cursor: pointer;
            border-radius: var(--borderRadius-md);
            margin: 0 var(--spacing-sm);
        }

        .sidebar-item:hover {
            background: var(--muted);
        }

        .sidebar-item.active {
            background: var(--primary);
            color: var(--primaryForeground);
        }

        .sidebar-icon {
            font-size: 16px;
            flex-shrink: 0;
            width: 24px;
            text-align: center;
        }

        .sidebar-label {
            overflow: hidden;
            opacity: 1;
            transition: opacity 0.15s ease;
        }

        .sidebar.collapsed .sidebar-label {
            opacity: 0;
            width: 0;
        }

        .sidebar-toggle {
            display: flex;
            align-items: center;
            justify-content: center;
            margin: var(--spacing-sm);
            padding: var(--spacing-sm);
            background: var(--muted);
            border: none;
            border-radius: var(--borderRadius-md);
            color: var(--foreground);
            cursor: pointer;
            font-size: 12px;
            transition: background 0.15s ease, transform 0.2s ease;
        }

        .sidebar.collapsed .sidebar-toggle {
            transform: rotate(180deg);
        }

        .sidebar-toggle:hover {
            background: var(--secondary);
        }

        .main-content {
            flex: 1;
            padding: var(--spacing-lg);
            overflow: auto;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: var(--spacing-lg);
            margin-bottom: var(--spacing-lg);
        }

        .card {
            background: var(--card);
            border: 1px solid var(--border);
            border-radius: var(--borderRadius-md);
            overflow: hidden;
            box-shadow: var(--shadow-sm);
            transition: transform 0.2s ease;
        }

        .card:hover {
            transform: scale(1.02);
        }

        .card-header {
            padding: var(--spacing-lg);
            border-bottom: 1px solid var(--border);
        }

        .card-title {
            font-size: var(--fontSize-xl);
            font-weight: var(--fontWeight-semibold);
            margin-bottom: var(--spacing-xs);
        }

        .card-description {
            font-size: var(--fontSize-base);
            color: var(--mutedForeground);
        }

        .card-body {
            padding: var(--spacing-lg);
        }

        .card-footer {
            padding: var(--spacing-md) var(--spacing-lg);
            border-top: 1px solid var(--border);
            background: var(--muted);
            display: flex;
            justify-content: flex-end;
        }

        .badge {
            display: inline-flex;
            align-items: center;
            padding: 2px var(--spacing-sm);
            border-radius: var(--borderRadius-full);
            font-size: var(--fontSize-xs);
            font-weight: var(--fontWeight-medium);
        }

        .badge-active {
            background: var(--active);
            color: var(--activeForeground);
        }

        .badge-inactive {
            background: var(--inactive);
            color: var(--inactiveForeground);
        }

        .badge-pending {
            background: var(--pending);
            color: var(--pendingForeground);
        }

        .btn {
            padding: var(--spacing-sm) var(--spacing-md);
            border-radius: var(--borderRadius-md);
            font-weight: var(--fontWeight-medium);
            cursor: pointer;
            transition: all 0.2s;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: var(--spacing-xs);
            font-size: var(--fontSize-base);
        }

        .btn-primary {
            background: var(--primary);
            color: var(--primaryForeground);
            border: 1px solid var(--primary);
        }

        .btn-primary:hover {
            background: var(--primaryForeground);
            color: var(--primary);
        }

        .heading-2 {
            font-size: var(--fontSize-2xl);
            font-weight: var(--fontWeight-semibold);
            line-height: var(--lineHeight-tight);
            color: var(--primary);
            margin-bottom: var(--spacing-md);
        }

        .table {
            width: 100%;
            border-collapse: collapse;
            font-size: var(--fontSize-base);
            border: 1px solid var(--border);
            border-radius: var(--borderRadius-md);
            overflow: hidden;
        }

        .table thead {
            background: var(--muted);
        }

        .table th {
            text-align: left;
            padding: var(--spacing-sm) var(--spacing-md);
            font-weight: var(--fontWeight-semibold);
            border-bottom: 1px solid var(--border);
        }

        .table td {
            padding: var(--spacing-sm) var(--spacing-md);
            border-bottom: 1px solid var(--border);
        }

        .table tr:last-child td {
            border-bottom: none;
        }

        @media (max-width: 768px) {
            .sidebar {
                width: 56px;
            }

            .sidebar-app-name, .sidebar-label, .sidebar-section-label {
                opacity: 0;
                width: 0;
            }
        }
    </style>
</head>
<body>
    <nav class="sidebar" id="sidebar">
        <div class="sidebar-header">
            <span class="sidebar-logo">⚡</span>
            <span class="sidebar-app-name">HealthBridge Admin</span>
        </div>
        <div class="sidebar-section">
            <p class="sidebar-section-label">MAIN</p>
            <a class="sidebar-item active" href="#">
                <span class="sidebar-icon">⊞</span>
                <span class="sidebar-label">Dashboard</span>
            </a>
            <a class="sidebar-item" href="#">
                <span class="sidebar-icon">📋</span>
                <span class="sidebar-label">Programs</span>
            </a>
            <a class="sidebar-item" href="#">
                <span class="sidebar-icon">👤</span>
                <span class="sidebar-label">Users</span>
            </a>
            <a class="sidebar-item" href="#">
                <span class="sidebar-icon">⚙</span>
                <span class="sidebar-label">Settings</span>
            </a>
        </div>
        <button class="sidebar-toggle" onclick="document.getElementById('sidebar').classList.toggle('collapsed')" aria-label="Toggle sidebar">◀</button>
    </nav>

    <main class="main-content">
        <h2 class="heading-2">Admin Program Management Dashboard</h2>
        <div class="dashboard-grid">
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Provider Enrollment</h3>
                    <p class="card-description">Acme Healthcare</p>
                </div>
                <div class="card-body">
                    <p>Status: <span class="badge badge-active">Active</span></p>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary">View</button>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Patient Enrollment</h3>
                    <p class="card-description">Beta Medical Group</p>
                </div>
                <div class="card-body">
                    <p>Status: <span class="badge badge-pending">Pending</span></p>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary">View</button>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Member Onboarding</h3>
                    <p class="card-description">Gamma Health Systems</p>
                </div>
                <div class="card-body">
                    <p>Status: <span class="badge badge-inactive">Inactive</span></p>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary">View</button>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <h3 class="card-title">Employee Enrollment</h3>
                    <p class="card-description">Delta Care Inc.</p>
                </div>
                <div class="card-body">
                    <p>Status: <span class="badge badge-active">Active</span></p>
                </div>
                <div class="card-footer">
                    <button class="btn btn-primary">View</button>
                </div>
            </div>
        </div>

        <h3>Recent Activity</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>Program</th>
                    <th>Activity</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Provider Enrollment (Acme)</td>
                    <td>Form submitted</td>
                    <td>2024-03-15</td>
                </tr>
                <tr>
                    <td>Patient Enrollment (Beta)</td>
                    <td>Program updated</td>
                    <td>2024-03-14</td>
                </tr>
                <tr>
                    <td>Member Onboarding (Gamma)</td>
                    <td>Status changed to Inactive</td>
                    <td>2024-03-13</td>
                </tr>
            </tbody>
        </table>
    </main>
</body>
</html>
```

---

## Admin Login

**Type:** auth
**Description:** This screen allows administrators to log in to the admin section of the application using their username and password. It is the entry point for accessing protected admin routes.

### HTML Prototype

```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Login</title>
  <style>
    :root {
      --primary: #e41f35;
      --primary-foreground: #ffffff;
      --secondary: #dedede;
      --secondary-foreground: #333333;
      --success: #fde69f;
      --success-foreground: #333333;
      --warning: #fde69f;
      --warning-foreground: #333333;
      --error: #e41f35;
      --error-foreground: #ffffff;
      --info: #bbdde6;
      --info-foreground: #333333;
      --background: #f3f3f3;
      --foreground: #333333;
      --muted: #efefef;
      --muted-foreground: #636363;
      --border: #efefef;
      --card: #ffffff;
      --card-foreground: #333333;
      --active: #e41f35;
      --active-foreground: #ffffff;
      --inactive: #cccccc;
      --inactive-foreground: #333333;
      --pending: #9370db;
      --pending-foreground: #ffffff;
      --fontFamily: Geist, Inter, -apple-system, sans-serif;
      --fontScale: compact;
      --fontSize-xs: 11px;
      --fontSize-sm: 12px;
      --fontSize-base: 13px;
      --fontSize-lg: 15px;
      --fontSize-xl: 18px;
      --fontSize-2xl: 22px;
      --fontSize-3xl: 28px;
      --fontWeight-normal: 400;
      --fontWeight-medium: 500;
      --fontWeight-semibold: 600;
      --fontWeight-bold: 700;
      --lineHeight-tight: 1.25;
      --lineHeight-normal: 1.5;
      --lineHeight-relaxed: 1.75;
      --spacing-unit: 4px;
      --spacing-xs: 4px;
      --spacing-sm: 8px;
      --spacing-md: 16px;
      --spacing-lg: 24px;
      --spacing-xl: 32px;
      --spacing-2xl: 48px;
      --borderRadius-none: 0;
      --borderRadius-sm: 4px;
      --borderRadius-md: 6px;
      --borderRadius-lg: 8px;
      --borderRadius-xl: 12px;
      --borderRadius-full: 9999px;
      --shadows-sm: 0 1px 2px rgba(0, 0, 0, 0.05);
      --shadows-md: 0 4px 6px rgba(0, 0, 0, 0.1);
      --shadows-lg: 0 10px 15px rgba(0, 0, 0, 0.1);
      --shadows-xl: 0 20px 25px rgba(0, 0, 0, 0.1);
    }

    body {
      font-family: var(--fontFamily);
      background: var(--background);
      color: var(--foreground);
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      margin: 0;
      padding: var(--spacing-lg);
    }

    .card {
      background: var(--card);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      overflow: hidden;
      box-shadow: var(--shadows-sm);
      width: 400px;
      padding: var(--spacing-lg);
    }

    .card-header {
      text-align: center;
      margin-bottom: var(--spacing-lg);
    }

    .card-title {
      font-size: var(--fontSize-xl);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .input-group {
      display: flex;
      flex-direction: column;
      margin-bottom: var(--spacing-sm);
    }

    .label {
      font-size: var(--fontSize-sm);
      font-weight: var(--fontWeight-semibold);
      margin-bottom: var(--spacing-xs);
    }

    .input {
      width: 100%;
      padding: var(--spacing-sm) var(--spacing-md);
      border: 1px solid var(--border);
      border-radius: var(--borderRadius-md);
      font-size: var(--fontSize-base);
      background: var(--card);
      color: var(--foreground);
      transition: border-color 0.2s;
    }

    .input:focus {
      outline: none;
      border-color: var(--primary);
    }

    .btn {
      padding: var(--spacing-sm) var(--spacing-md);
      border-radius: var(--borderRadius-md);
      font-weight: var(--fontWeight-medium);
      cursor: pointer;
      transition: all 0.2s;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      gap: var(--spacing-xs);
      border: none;
      font-size: var(--fontSize-base);
    }

    .btn-primary {
      background: var(--primary);
      color: var(--primary-foreground);
      border: 1px solid var(--primary);
    }

    .btn-primary:hover {
      background: var(--primary-foreground);
      color: var(--primary);
      border: 1px solid var(--primary);
    }

    .auth-links {
      display: flex;
      justify-content: space-between;
      font-size: var(--fontSize-sm);
      margin-top: var(--spacing-md);
    }

    .auth-links a {
      color: var(--primary);
      text-decoration: none;
    }

    .auth-links a:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
  <div class="card">
    <div class="card-header">
      <h2 class="card-title">Admin Login</h2>
    </div>
    <form>
      <div class="input-group">
        <label class="label" for="username">Username</label>
        <input type="text" id="username" class="input" placeholder="Enter username">
      </div>
      <div class="input-group">
        <label class="label" for="password">Password</label>
        <input type="password" id="password" class="input" placeholder="Enter password">
      </div>
      <button class="btn btn-primary">Login</button>
    </form>
    <div class="auth-links">
      <a href="#">Forgot password?</a>
      <a href="#">Create account</a>
    </div>
  </div>
</body>
</html>
```

---

