<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# FacilityIntellij Changelog

## [Unreleased]
### Added
- Inspection for duplicate field names, enum, and error values
- Intention to add `[validate]` to enum declarations
- Live Templates for validation attributes
  - svalid: `[validate(regex: $REGEX$, length: $RANGE$)]`
  - nvalid: `[validate(value: $RANGE$)]`
  - cvalid: `[validate(count: $RANGE$)]`
- Intention to add the appropriate validate template (or just `[validate]` when enum-typed) fields

### Fixed
- Code folding for method, enum, and error bodies

## [0.0.11]
- Added support for the 2022.2 Intellij platform

## [0.0.10]
- Added support for the 2022.1 Intellij platform

## [0.0.9]
### Added
- Declaration type icons in autocomplete UI

### Fixed
- Methods are shown in autocomplete
- Re-indent does not handle comments nor non-data service items well

## [0.0.8]
### Added
- Show for method and service definitions in the structure view
- Icons for presented PSI elements (like method, data, enum, etc). <br/> These appear in the structure view and are helpful in distinguishing between language constructs.

### Fixed
- Renaming enums and error sets converts the declaration to `data`
- Use non-eap qodana for CI

## [0.0.7]
### Added
- Code folding
- Documentation comment tooltips
- Support `[required]` shorthand `!`
- Match paired braces

### Fixed
- Improved automatic indenting while typing

## [0.0.6]
### Added
- Automatically indent code
- Autocomplete built-in types

## [0.0.5]
### Added
- Rename types with inline refactoring
- Autocomplete user-defined types
- Find usages for user-defined types
- Comment or uncomment lines with editor actions
- Support for structure view
- Support for go to symbol

## 0.0.4
### Changed
- The plugin description conforms to JetBrains guidelines

## 0.0.3
### Changed
- Point references to github project at the FacilityApi organization

## 0.0.2
### Added
- Icon and description to the plugin marketplace page

## 0.0.1
### Added
- FSD file recognition
- FSD syntax highlighting
- Color Scheme settings for FSD
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
