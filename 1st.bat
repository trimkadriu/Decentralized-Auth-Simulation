@echo off
cls
set role=SR_NODE
if not [%1]==[] (
	set role=%1
)

java -jar target/dec-auth-sim.jar -id 1 -r %role% -pbk MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBANlKuoA+5QENVphQcVg51CMizjzt4H+3FOxexZCGhKcrGpb9cR8vRJ4nWtPMeLiC0vGhh7/271PlYAHRYXSAZdUCAwEAAQ== -prk MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA2Uq6gD7lAQ1WmFBxWDnUIyLOPO3gf7cU7F7FkIaEpysalv1xHy9Enida08x4uILS8aGHv/bvU+VgAdFhdIBl1QIDAQABAkB732cczz4b8G+qW0mSYz01XpU2FOIDzOpnqct1Dcq3xQQWtWK47aA9wL9LgIxO/gn+KR8RYdhsYxR6fudCUedhAiEA/5X0wFOBSZ7Fvwr2tFQwKLEWmhrISh4ai7nk8Lh4AokCIQDZpOJWLogu7UH1YWI1WC9+VftlgwZ3Vz9bOnorj7Zl7QIhAINe+rzYrz2+JHp8Ei7CjzZ7P9jaJ1UhgBeN54Vr7BOxAiEAqi9CRL2SA4/GmceSccAJMzKsp83yynLQuddaHKH9sGkCIQDuQXmZTQLkyGTiqrKA5JbwKCQsTUDd/HuMM8fqvMoxxA==