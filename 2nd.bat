@echo off
cls
set role=SP_NODE
if not [%1]==[] (
	set role=%1
)

java -jar target/dec-auth-sim.jar -id 2 -r %role% -pbk MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMj/mJri58jpWcOh9RfqzdM1kRQAoDz5SgXyi0qcpubQ8w0KUikP6oK6YLXU0hNyYRRSaoxwpOI2g4ZjC2s8S7kCAwEAAQ== -prk MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAyP+YmuLnyOlZw6H1F+rN0zWRFACgPPlKBfKLSpym5tDzDQpSKQ/qgrpgtdTSE3JhFFJqjHCk4jaDhmMLazxLuQIDAQABAkA6nY87XACMLsDmtExtdSQg+/YbBzOzlF9p7JmCYLyrgq9kvUsnHapCKPIdsYASXiIg2hf9FZdevq/6QwGO7yABAiEA7rUYHvWydk9BneMt3YgdM3MWfd0s8iCR1hTSGfVPyjkCIQDXjy0+0mBUKcjbmDPlz9SixFNGELQMEWUK+sudUNaNgQIgA7nh8YHGUmB0VsGYErAwPIBcZoSQUgs+G4hhqzQQFckCIQDWcbvDWas3T3Jg8+P9ZmrVmpZ1h7x2nP92ktXppTgtAQIhANfukeERyDpsp3WtKkNnw/befIJgqB1j6Ofh8JP+iHg/