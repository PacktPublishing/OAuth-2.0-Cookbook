-- defines resource server credentials for token introspection
INSERT INTO oauth_client_details (
  client_id, resource_ids, client_secret, scope, authorized_grant_types,
  web_server_redirect_uri, authorities, access_token_validity,
  refresh_token_validity, additional_information, autoapprove)
VALUES (
  'resource_server', '', 'abc123', 'read_profile,write_profile',
  'authorization_code', 'http://localhost:9000/callback', 'introspection',
  null, null, null, '');

-- creates a client details
INSERT INTO oauth_client_details (
  client_id, resource_ids, client_secret, scope, authorized_grant_types,
  web_server_redirect_uri, authorities, access_token_validity,
  refresh_token_validity, additional_information, autoapprove)
VALUES (
  'clientxpto', '', '123', 'read_profile,write_profile',
  'authorization_code,password', 'http://localhost:9000/callback', null,
  null, null, null, '');

-- creates user database (resource owner table)
create table resource_owner(
  id bigint auto_increment primary key,
  name varchar(200),
  username varchar(60),
  password varchar(100),
  email varchar(100)
);

insert into resource_owner (name, username, password, email)
values ('Adolfo Eloy', 'adolfo', '123', 'adolfo@mailinator.com');
