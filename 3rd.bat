@echo off
cls
set role=MINER_NODE
if not [%1]==[] (
	set role=%1
)

java -jar target/dec-auth-sim.jar -id 3 -r %role% -pbk MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALK7Y8zwfViB2BQR8qyMkszyu5Oze76zIAARVvBnkNsZRhd6xt9MA0+UFDt8TT/XFFmC8nJWTklSFl61xe9G1GsCAwEAAQ== -prk MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAsrtjzPB9WIHYFBHyrIySzPK7k7N7vrMgABFW8GeQ2xlGF3rG30wDT5QUO3xNP9cUWYLyclZOSVIWXrXF70bUawIDAQABAkEAltzhcp0T0F9ZGifEJK1LRD8m4ZdAPklIG4aHUci5VpVm/WOZi46VSy7ICUSF5v6BPELufxK3snWZMZP8gkwrwQIhAOLrRN1QC1z47jyg6xAXZtP6dI+lJgBydrsJJuTnXc0RAiEAyaM08ghHIkyrgp5soK93WXNMIFsP0prnCfaGkS6FebsCICA29lfU/Uf9rxS8Y6KtL9P4QoQCFhsKJQD4k6T7k72BAiADZ+lWCUPCVY80Fp75G92lOihYB5G92fX7Ghnty95fTwIhAIzr2L1eggVqm6r3OeLgIT7ZZHgJEiee8hZzAXs8/4z9